package com.example.UserAuthenticationVA.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND,reason = "User Already Exists")
public class UserAlreadyExistsException extends Exception{
}
