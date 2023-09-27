package com.example.VehicleAppForUSerAndAdmin.VehicleTesting;

import com.example.VehicleAppForUSerAndAdmin.controller.VehicleController;
import com.example.VehicleAppForUSerAndAdmin.domain.Vehicle;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleNotFoundException;
import com.example.VehicleAppForUSerAndAdmin.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {

    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehicleService vehicleService;

    @Test
    void testAddVehicle_Success() throws VehicleAlreadyExistsException {
        Vehicle vehicle = new Vehicle("vehicleId", "Car", "Sports car");
        when(vehicleService.addVehicle(vehicle)).thenReturn(vehicle);

        ResponseEntity<?> responseEntity = vehicleController.addVehicle(vehicle);

        verify(vehicleService, times(1)).addVehicle(vehicle);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(vehicle, responseEntity.getBody());
    }

    @Test
    void testGetAllVehicles_Success() throws VehicleNotFoundException {
        when(vehicleService.getAllVehicles()).thenReturn(new ArrayList<>());

        ResponseEntity<?> responseEntity = vehicleController.getAllVehicles();

        verify(vehicleService, times(1)).getAllVehicles();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteVehicle_Success() throws VehicleNotFoundException {
        String vehicleId = "vehicleId";
        Vehicle vehicle= new Vehicle(vehicleId, "Car", "Deleted car");
        when(vehicleService.deleteVehicle(vehicleId)).thenReturn(Collections.singletonList(vehicle));

        ResponseEntity<?> responseEntity = vehicleController.deleteVehicle(vehicleId);

        verify(vehicleService, times(1)).deleteVehicle(vehicleId);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.singletonList(vehicle), responseEntity.getBody());
    }


    @Test
    void testUpdateVehicle_Success() throws VehicleNotFoundException {
        Vehicle vehicle = new Vehicle("vehicleId", "Car", "Updated car");
        when(vehicleService.updateVehicle(vehicle)).thenReturn(vehicle);

        ResponseEntity<?> responseEntity = vehicleController.updateVehicle(vehicle);

        verify(vehicleService, times(1)).updateVehicle(vehicle);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(vehicle, responseEntity.getBody());
    }
}
