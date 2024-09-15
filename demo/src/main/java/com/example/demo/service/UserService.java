package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public void registerUser(String username, String password) {
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
    }

	public User findByUsername(String username) {
		List<User> s=userRepository.findAll();
		for(int i=0;i<s.size();i++) {
			if(s.get(i).getUsername().equals(username)) {
				return s.get(i);
			}
		}
		return null;
	}
	

}
