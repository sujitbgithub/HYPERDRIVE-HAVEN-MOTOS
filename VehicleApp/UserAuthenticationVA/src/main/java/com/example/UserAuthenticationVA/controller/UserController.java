package com.example.UserAuthenticationVA.controller;


import com.example.UserAuthenticationVA.domain.User;
import com.example.UserAuthenticationVA.exception.UserAlreadyExistsException;
import com.example.UserAuthenticationVA.exception.UserNotFoundException;
import com.example.UserAuthenticationVA.security.SecurityTokenGeneration;
import com.example.UserAuthenticationVA.security.SecurityTokenGenerationIMPL;
import com.example.UserAuthenticationVA.service.UserServiceIMPL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user-auth/v1")
public class UserController {

    private UserServiceIMPL userServiceIMPL;
    private SecurityTokenGenerationIMPL securityTokenGenerationIMPL;

    public UserController(UserServiceIMPL userServiceIMPL, SecurityTokenGenerationIMPL securityTokenGenerationIMPL) {

        this.userServiceIMPL = userServiceIMPL;
        this.securityTokenGenerationIMPL = securityTokenGenerationIMPL;
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws UserNotFoundException{

    try {
        Map<String, String> UserMap=null;
        User userObject= userServiceIMPL.findByEmailIdAndPassword(user.getEmailId(), user.getPassword());
            if (userObject.getEmailId().equals(user.getEmailId())){
                UserMap=securityTokenGenerationIMPL.generateToken(user);
            }
        return new ResponseEntity<>(UserMap, HttpStatus.OK);
    }
    catch (UserNotFoundException userNotFoundException){
        return new ResponseEntity<>("User Details Not Found", HttpStatus.NOT_FOUND);

    }
    catch (Exception exception){
        return new ResponseEntity<>("There Is Some Error, Please Try After Some Time", HttpStatus.REQUEST_TIMEOUT);
    }
    }


    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(@RequestBody User user) throws UserAlreadyExistsException{
        try {
                return new ResponseEntity<>(userServiceIMPL.addUser(user),HttpStatus.OK);
        }
        catch (UserAlreadyExistsException userAlreadyExistsException){
                return new ResponseEntity<>("User Already Exists", HttpStatus.CONFLICT);
        }
        catch (Exception exception){
            return new ResponseEntity<>("There Is Some Error, Please Try After Some Time", HttpStatus.REQUEST_TIMEOUT);

        }
    }

}
