package com.example.UserAuthenticationVA.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Unable to Find User")
public class UserNotFoundException extends  Exception{
}
