package com.nsp.cowsandbullss.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nsp.cowsandbullss.model.User;
import com.nsp.cowsandbullss.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private UserService userService;
    
    private String secretNumber;
    private User currentUser;
    
    
    
    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody Map<String, String> input) {
        String username = input.get("username");
        String password = input.get("password");

        // Check if user already exists
        User existingUser = userService.findByUsername(username);
        if (existingUser != null) {
        	return Map.of("success", false, "message", "User already exists. Please login."); 
        	}
        try{
        	userService.registerUser(username, password);//Register new user
        		
            return Map.of("success", true, "message", "Userv already registration successfully. Please login.");
        }catch(IllegalArgumentException e){
        	 return Map.of("success", false, "message", e.getMessage());
        }
       
    }
    // Login User
    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestBody Map<String, String> input) {
    	String username = input.get("username");
    	String password = input.get("password");
    	User user = userService.loginUser(username, password);
    	if (user != null)
    	{ 
    		currentUser = user;
    		return Map.of("success", true, "message", "Login successful!");
    	}
    	else
    	{
    		return Map.of("success", false, "message", "Invalid username or password."); }
    }
    
    // Logout User
    @PostMapping("/logout")
    public String logoutUser() {
    	currentUser = null;
   
    	return "User logged out successfully!";
    	}
    
    // Start a new game
    @GetMapping("/start")
    public String startGame() {
        secretNumber = generateRandomNumber();
        return "Game started! Try guessing the 4-digit number.";
    }

    // Guess the number
    @PostMapping("/guess")
    public Map<String, Object> guessNumber(@RequestBody Map<String, String> input) {
        if (secretNumber == null) {
            return Map.of("error", "Game not started. Please start a new game first.");
        }

        String guess = input.get("guess");
        if (guess == null || !isValidGuess(guess)) {
            return Map.of("error", "Invalid input. Please enter a 4-digit number with unique digits.");
        }

        int bulls = 0, cows = 0;
        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == secretNumber.charAt(i)) {
                bulls++;
            } else if (secretNumber.contains(String.valueOf(guess.charAt(i)))) {
                cows++;
            }
        }

       if (bulls == 4) {
    	   userService.saveScore(currentUser, 100); // Example score for a correct guess 
    	   return Map.of("message", "Congratulations! You've guessed the correct number!", "bulls", bulls, "cows", cows, "success", true); 
    	   } else {
    		   userService.saveScore(currentUser, 10); // Example score for an incorrect guess 
    		   return Map.of("bulls", bulls, "cows", cows, "success", false); 
    		   }
    }
    // Generate a random 4-digit number with unique digits
    private String generateRandomNumber() {
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i < 10; i++) digits.add(i);
        Collections.shuffle(digits);

        StringBuilder number = new StringBuilder();
        for (int i = 0; i < 4; i++) number.append(digits.get(i));

        System.out.println("Generated Secret Number (DEBUG): " + number); // For debugging
        return number.toString();
    }

    // Validate if the input is a 4-digit number with unique digits
    private boolean isValidGuess(String guess) {
        if (guess == null || guess.length() != 4 || !guess.matches("\\d+")) {
            return false;
        }

        Set<Character> uniqueDigits = new HashSet<>();
        for (char c : guess.toCharArray()) {
            uniqueDigits.add(c);
        }
        return uniqueDigits.size() == 4;
    }
}