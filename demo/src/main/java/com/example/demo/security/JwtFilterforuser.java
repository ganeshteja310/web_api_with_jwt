package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import java.io.IOException;

@Component
public class JwtFilterforuser extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService user;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	 if ("/api/login".equals(request.getRequestURI())) {
             chain.doFilter(request, response);
             return;
         }
    	 else {
    	 System.out.println("--------------------------");
        String authorizationHeader = request.getHeader("Authorization");
        //System.out.println(authorizationHeader+"----auth");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                String username = jwtUtil.extractUsername(token);
                if (username != null && jwtUtil.validateToken(token, username)) {
                	System.out.println("Valid");
                	User userDetails = user.findByUsername(username); 
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, null
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token");
                    return;
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token validation error");
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No JWT token found in request");
            return;
        }

        chain.doFilter(request, response);
    }
    }
}
