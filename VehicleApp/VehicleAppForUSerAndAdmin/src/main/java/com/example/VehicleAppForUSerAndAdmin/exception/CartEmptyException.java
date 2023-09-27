package com.example.VehicleAppForUSerAndAdmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Cart Is Empty")
public class CartEmptyException extends Exception{
}
