package com.example.VehicleAppForUSerAndAdmin.VehicleTesting;

import com.example.VehicleAppForUSerAndAdmin.domain.Vehicle;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleNotFoundException;
import com.example.VehicleAppForUSerAndAdmin.repository.IVehicleRepository;
import com.example.VehicleAppForUSerAndAdmin.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private IVehicleRepository iVehicleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddVehicle_Success() throws VehicleAlreadyExistsException {
        Vehicle vehicle = new Vehicle("vehicleId", "Car", "Sports car");
        when(iVehicleRepository.findById(vehicle.getVehicleId())).thenReturn(Optional.empty());
        when(iVehicleRepository.save(vehicle)).thenReturn(vehicle);

        Vehicle result = vehicleService.addVehicle(vehicle);

        verify(iVehicleRepository, times(1)).findById(vehicle.getVehicleId());
        verify(iVehicleRepository, times(1)).save(vehicle);
        assertNotNull(result);
        assertEquals(vehicle.getVehicleId(), result.getVehicleId());
    }

    @Test
    void testGetAllVehicles_Success() throws VehicleNotFoundException {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("vehicleId1", "Car", "Sports car"));
        vehicles.add(new Vehicle("vehicleId2", "Scorpio", "SUV"));
        when(iVehicleRepository.findAll()).thenReturn(vehicles);

        List<Vehicle> result = vehicleService.getAllVehicles();

        verify(iVehicleRepository, times(1)).findAll();
        assertNotNull(result);
        assertEquals(vehicles.size(), result.size());
    }

    @Test
    void testDeleteVehicle_Success() throws VehicleNotFoundException {
        String vehicleId = "vehicleId";
        Vehicle vehicle = new Vehicle(vehicleId, "Car", "Deleted car");
        when(iVehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(iVehicleRepository.findAll()).thenReturn(new ArrayList<>());

        List<Vehicle> result = vehicleService.deleteVehicle(vehicleId);

        verify(iVehicleRepository, times(1)).findById(vehicleId);
        verify(iVehicleRepository, times(1)).deleteById(vehicleId);
        verify(iVehicleRepository, times(1)).findAll();
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testUpdateVehicle_Success() throws VehicleNotFoundException {
        Vehicle vehicle = new Vehicle("vehicleId", "Car", "Updated car");
        when(iVehicleRepository.findById(vehicle.getVehicleId())).thenReturn(Optional.of(vehicle));
        when(iVehicleRepository.save(vehicle)).thenReturn(vehicle);

        Vehicle updatedVehicle = new Vehicle(vehicle.getVehicleId(), "Updated Car", "Updated Details");
        Vehicle result = vehicleService.updateVehicle(updatedVehicle);

        verify(iVehicleRepository, times(1)).findById(vehicle.getVehicleId());
        verify(iVehicleRepository, times(1)).save(vehicle);
        assertNotNull(result);
        assertEquals(updatedVehicle.getVehicleName(), result.getVehicleName());
        assertEquals(updatedVehicle.getVehicleDetails(), result.getVehicleDetails());
    }
}
