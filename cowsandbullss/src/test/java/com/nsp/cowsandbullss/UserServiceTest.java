package com.nsp.cowsandbullss;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nsp.cowsandbullss.model.User;
import com.nsp.cowsandbullss.repository.UserRepository;
import com.nsp.cowsandbullss.service.UserService;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User createdUser = userService.registerUser("testuser", "password");

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
    }

    @Test
    public void testLoginUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(user);

        User loggedInUser = userService.loginUser("testuser", "password");

        assertNotNull(loggedInUser);
        assertEquals("testuser", loggedInUser.getUsername());

        User invalidUser = userService.loginUser("testuser", "wrongpassword");
        assertNull(invalidUser);
    }

    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setUsername("testuser");

        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = userService.findByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());

        User notFoundUser = userService.findByUsername("nonexistentuser");
        assertNull(notFoundUser);
    }

    @Test
    public void testRegisterUserWithDuplicateUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(user);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser("testuser", "newpassword");
        });

        assertEquals("User already exists", exception.getMessage());
    }
}
