package com.example.VehicleAppForUSerAndAdmin.service;

import com.example.VehicleAppForUSerAndAdmin.domain.Vehicle;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleNotFoundException;

import java.util.List;

public interface IVehicleService {

    Vehicle addVehicle(Vehicle vehicle) throws VehicleAlreadyExistsException;

    List<Vehicle> getAllVehicles() throws VehicleNotFoundException;

    List<Vehicle> deleteVehicle(String vehicleId) throws VehicleNotFoundException;

    Vehicle updateVehicle(Vehicle vehicle) throws VehicleNotFoundException;
}
