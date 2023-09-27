package com.example.VehicleAppForUSerAndAdmin.service;

import com.example.VehicleAppForUSerAndAdmin.domain.User;
import com.example.VehicleAppForUSerAndAdmin.domain.Vehicle;
import com.example.VehicleAppForUSerAndAdmin.exception.CartEmptyException;
import com.example.VehicleAppForUSerAndAdmin.exception.UserAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.exception.UserNotFoundException;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleAlreadyExistsException;

import java.util.List;


public interface IUserService {

    User registerUser(User user) throws UserAlreadyExistsException;

    User addVehicleToCart(String emailId, Vehicle vehicle) throws VehicleAlreadyExistsException, UserNotFoundException;

    List<Vehicle> getAllVehicles(String emailId) throws UserNotFoundException, CartEmptyException;

    List<Vehicle> deleteVehicleFromCart(String emailId, String vehicleId) throws UserNotFoundException;
}
