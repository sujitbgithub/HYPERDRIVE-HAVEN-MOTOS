package com.example.VehicleAppForUSerAndAdmin.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,  reason = "No Such User Exists")
public class UserNotFoundException extends Exception{
}
