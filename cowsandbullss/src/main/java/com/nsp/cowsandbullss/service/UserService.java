package com.nsp.cowsandbullss.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nsp.cowsandbullss.model.Score;
import com.nsp.cowsandbullss.model.User;
import com.nsp.cowsandbullss.repository.ScoreRepository;
import com.nsp.cowsandbullss.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScoreRepository scoreRepository;

    public User registerUser(String username, String password) {
        User user= new User();          //create and save new user
        user.setUsername(username);  
        user.setPassword(password);
		return userRepository.save(user); 
    	}
    
    public User findByUsername(String username) {        //fetch the user
        return userRepository.findByUsername(username);
    }
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User loginUser(String username, String password) {
    	User user = userRepository.findByUsername(username);   
    	if (user != null && user.getPassword().equals(password))
    	{ 
    		return user; //to find user is registerd or not if registered get the user
    		}
    	return null; 
    	}
    
    public Score saveScore(User user, int score) { 
    	Score newScore = new Score();
    	newScore.setUser(user); 
    	newScore.setScore(score); 
    	return scoreRepository.save(newScore);
    	}
}
