package com.example.VehicleAppForUSerAndAdmin.VehicleTesting;

import com.example.VehicleAppForUSerAndAdmin.domain.Vehicle;
import com.example.VehicleAppForUSerAndAdmin.repository.IVehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class VehicleRepositoryTest {

    @Autowired
    private IVehicleRepository vehicleRepository;

    @Test
    void testFindById_Success() {
        String vehicleId = "vehicleId1";
        Vehicle vehicle = new Vehicle(vehicleId, "Car", "Sports car");
        vehicleRepository.save(vehicle);

        Optional<Vehicle> result = vehicleRepository.findById(vehicleId);

        assertTrue(result.isPresent());
        assertEquals(vehicleId, result.get().getVehicleId());
    }

    @Test
    void testFindById_NotFound() {
        String vehicleId = "nonExistentId";

        Optional<Vehicle> result = vehicleRepository.findById(vehicleId);

        assertFalse(result.isPresent());
    }

    @Test
    void testSave_Success() {
        String vehicleId = "vehicleId2";
        Vehicle vehicle = new Vehicle(vehicleId, "Scorpio", "SUV");

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        assertNotNull(savedVehicle);
        assertEquals(vehicleId, savedVehicle.getVehicleId());
    }

    @Test
    void testDeleteById_Success() {
        String vehicleId = "vehicleId3";
        Vehicle vehicle = new Vehicle(vehicleId, "Truck", "Heavy-duty truck");
        vehicleRepository.save(vehicle);

        vehicleRepository.deleteById(vehicleId);

        assertFalse(vehicleRepository.existsById(vehicleId));
    }


}
