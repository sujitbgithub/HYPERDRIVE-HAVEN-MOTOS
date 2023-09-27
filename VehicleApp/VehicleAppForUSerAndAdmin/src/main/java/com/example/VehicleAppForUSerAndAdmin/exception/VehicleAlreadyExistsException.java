package com.example.VehicleAppForUSerAndAdmin.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Vehicle Already Regsitered")
public class VehicleAlreadyExistsException extends Exception{
}
