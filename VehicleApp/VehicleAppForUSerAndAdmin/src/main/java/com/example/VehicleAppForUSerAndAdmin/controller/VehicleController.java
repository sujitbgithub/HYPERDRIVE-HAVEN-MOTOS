package com.example.VehicleAppForUSerAndAdmin.controller;


import com.example.VehicleAppForUSerAndAdmin.domain.Vehicle;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleNotFoundException;
import com.example.VehicleAppForUSerAndAdmin.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle/v1")
public class VehicleController {

    private VehicleService vehicleService;
    @Autowired
    public VehicleController(VehicleService vehicleService){this.vehicleService=vehicleService;}

    @PostMapping("/addVehicle")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle) throws  VehicleAlreadyExistsException {
        Vehicle addedVehicle = vehicleService.addVehicle(vehicle);
        if (addedVehicle == null) {
            return new ResponseEntity<>("Error Occurred while Adding New Vehicle", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(addedVehicle, HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/getAllVehicles")
    public ResponseEntity<?> getAllVehicles() throws VehicleNotFoundException {
        return new ResponseEntity<>(vehicleService.getAllVehicles(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteVehicle/{vehicleId}")
    public ResponseEntity<?> deleteVehicle(@PathVariable String vehicleId) throws  VehicleNotFoundException {
        return new ResponseEntity<>(vehicleService.deleteVehicle(vehicleId), HttpStatus.OK);
    }

    @PutMapping("/updateVehicle")
    public ResponseEntity<?> updateVehicle(@RequestBody Vehicle vehicle) throws VehicleNotFoundException {
        System.out.println("Vehicle is Updated");
        return new ResponseEntity<>(vehicleService.updateVehicle(vehicle), HttpStatus.OK);
    }

}
