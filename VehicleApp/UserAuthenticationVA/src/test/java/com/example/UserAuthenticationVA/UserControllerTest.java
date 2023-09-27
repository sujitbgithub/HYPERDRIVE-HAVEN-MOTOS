package com.example.UserAuthenticationVA;

import com.example.UserAuthenticationVA.controller.UserController;
import com.example.UserAuthenticationVA.domain.User;
import com.example.UserAuthenticationVA.exception.UserAlreadyExistsException;
import com.example.UserAuthenticationVA.exception.UserNotFoundException;
import com.example.UserAuthenticationVA.security.SecurityTokenGenerationIMPL;
import com.example.UserAuthenticationVA.service.UserServiceIMPL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserServiceIMPL userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private SecurityTokenGenerationIMPL securityTokenGeneration;


    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() throws UserAlreadyExistsException, JsonProcessingException {
        User user = new User("test@example.com", "password");
        when(userService.addUser(any(User.class))).thenReturn(user);

        ResponseEntity<?> responseEntity = userController.addNewUser(user);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expectedResponseBody = "{\"emailId\":\"test@example.com\",\"password\":\"password\"}";
        String responseBody = objectMapper.writeValueAsString(responseEntity.getBody());
        assertEquals(expectedResponseBody, responseBody);

        verify(userService, times(1)).addUser(any(User.class));
    }

//    @Test
//    void testLoginUser_Success() throws UserNotFoundException, JsonProcessingException {
//        User user = new User("test@example.com", "password");
//        when(userService.findByEmailIdAndPassword(user.getEmailId(), user.getPassword())).thenReturn(user);
//
//        ResponseEntity<?> responseEntity = userController.loginUser(user);
//        System.out.println(responseEntity.getStatusCode());
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//        String expectedResponseBody = "{\"emailId\":\"test@example.com\",\"password\":\"password\"}";
//
//        String responseBody = objectMapper.writeValueAsString(responseEntity.getBody());
//        System.out.println(responseBody);
//        assertEquals(expectedResponseBody, responseBody);
//
//        verify(userService, times(1)).findByEmailIdAndPassword(user.getEmailId(), user.getPassword());
//    }

    @Test
    void testLoginUser_Success() throws UserNotFoundException {
        // Mocking input user data
        User user = new User("test@example.com", "password");

        // Mocking the behavior of userServiceIMPL
        when(userService.findByEmailIdAndPassword(user.getEmailId(), user.getPassword())).thenReturn(user);

        // Mocking the behavior of securityTokenGenerationIMPL
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("Token", "exampleToken");
        when(securityTokenGeneration.generateToken(user)).thenReturn(tokenMap);

        // Calling the controller method
        ResponseEntity<?> responseEntity = userController.loginUser(user);

        // Verify that the methods were called with the expected arguments
        verify(userService, times(1)).findByEmailIdAndPassword(user.getEmailId(), user.getPassword());
        verify(securityTokenGeneration, times(1)).generateToken(user);

        // Assert the response status code
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Assert the response body
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Map);

        Map<String, String> responseBody = (Map<String, String>) responseEntity.getBody();
        assertEquals("exampleToken", responseBody.get("Token"));
    }

    @Test
    void testLoginUser_UserNotFound() throws UserNotFoundException {
        User user = new User("test@example.com", "password");
        when(userService.findByEmailIdAndPassword(user.getEmailId(), user.getPassword())).thenThrow(UserNotFoundException.class);

        ResponseEntity<?> responseEntity = userController.loginUser(user);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(userService, times(1)).findByEmailIdAndPassword(user.getEmailId(), user.getPassword());
    }

}
