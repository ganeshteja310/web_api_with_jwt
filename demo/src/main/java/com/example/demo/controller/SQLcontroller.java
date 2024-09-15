package com.example.demo.controller;

import com.example.demo.security.JwtUtil;
import com.example.demo.security.JwtFilterforuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SQLcontroller {

    private static final String url = "jdbc:postgresql://localhost:5432/sample";
    private static final String user = "postgres";
    private static final String password = "postgres"; 

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtFilterforuser jwtFilter;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> uploadSQLDump(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        System.out.println("Received upload request.");

        try {
            InputStream inputStream = file.getInputStream();
            String sqlCommands = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));

            Connection postgresConnection = DriverManager.getConnection(url, user, password);
            postgresConnection.setAutoCommit(false);

            Statement stmt = postgresConnection.createStatement();
            stmt.execute(sqlCommands);
            stmt.close();

            DatabaseMetaData metaData = postgresConnection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            Map<String, List<Map<String, Object>>> databaseContents = new HashMap<>();

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                Statement queryStmt = postgresConnection.createStatement();
                ResultSet resultSet = queryStmt.executeQuery("SELECT * FROM " + tableName);
                List<Map<String, Object>> tableRecords = new ArrayList<>();
                ResultSetMetaData rsmd = resultSet.getMetaData();

                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        row.put(rsmd.getColumnName(i), resultSet.getObject(i));
                    }
                    tableRecords.add(row);
                }

                databaseContents.put(tableName, tableRecords);
                resultSet.close();
                queryStmt.close();
            }

            postgresConnection.commit();
            postgresConnection.close();
            return ResponseEntity.ok(databaseContents);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}