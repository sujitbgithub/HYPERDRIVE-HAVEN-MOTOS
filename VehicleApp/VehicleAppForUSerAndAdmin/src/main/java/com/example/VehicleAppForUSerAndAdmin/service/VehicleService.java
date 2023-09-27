package com.example.VehicleAppForUSerAndAdmin.service;

import com.example.VehicleAppForUSerAndAdmin.domain.Vehicle;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleNotFoundException;
import com.example.VehicleAppForUSerAndAdmin.repository.IVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class VehicleService implements IVehicleService{

    private IVehicleRepository iVehicleRepository;

    @Autowired
    public VehicleService(IVehicleRepository iVehicleRepository){
        this.iVehicleRepository=iVehicleRepository;
    }
    @Override
    public Vehicle addVehicle(Vehicle vehicle) throws VehicleAlreadyExistsException {
        if (iVehicleRepository.findById(vehicle.getVehicleId()).isPresent())
            {
                throw new VehicleAlreadyExistsException();
            }
        else {
            return iVehicleRepository.save(vehicle);
        }
    }

    @Override
    public List<Vehicle> getAllVehicles() throws VehicleNotFoundException {
            return  iVehicleRepository.findAll();
    }

    @Override
    public List<Vehicle> deleteVehicle(String vehicleId) throws VehicleNotFoundException {
        if (iVehicleRepository.findById(vehicleId).isEmpty()){
            throw new VehicleNotFoundException();
        }
        else {
            iVehicleRepository.deleteById(vehicleId);
            return iVehicleRepository.findAll();
        }
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) throws VehicleNotFoundException {
        Optional<Vehicle> optionalVehicle = iVehicleRepository.findById(vehicle.getVehicleId());

        if (optionalVehicle.isPresent()) {            Vehicle alreadyRegisteredVehicle = optionalVehicle.get();
            // Update the properties if not empty
            if (!vehicle.getVehicleName().isEmpty()) {
                alreadyRegisteredVehicle.setVehicleName(vehicle.getVehicleName());
            }
            if (!vehicle.getVehicleDetails().isEmpty()) {
                alreadyRegisteredVehicle.setVehicleDetails(vehicle.getVehicleDetails());
            }
            return iVehicleRepository.save(alreadyRegisteredVehicle);
        } else {
            throw new VehicleNotFoundException();
        }
    }
//    public Vehicle updateVehicle(Vehicle vehicle) throws VehicleNotFoundException {
//        Vehicle alreadyRegisteredVehicle=iVehicleRepository.findById(vehicle.getVehicleId()).get();
//        if (alreadyRegisteredVehicle==null){
//            throw new VehicleNotFoundException();
//        }
//        if (vehicle.getVehicleName().isEmpty())
//        {
//            alreadyRegisteredVehicle.setVehicleName(vehicle.getVehicleName());
//        }
//        if (vehicle.getVehicleDetails().isEmpty())
//        {
//            alreadyRegisteredVehicle.setVehicleDetails(vehicle.getVehicleDetails());
//        }
//
//        return iVehicleRepository.save(alreadyRegisteredVehicle);
//    }
    /*public Vehicle updateVehicle(Vehicle vehicle) throws VehicleNotFoundException {
    Optional<Vehicle> optionalVehicle = iVehicleRepository.findById(vehicle.getVehicleId());

    if (optionalVehicle.isPresent()) {
        Vehicle alreadyRegisteredVehicle = optionalVehicle.get();
        // Update the properties if not empty
        if (!vehicle.getVehicleName().isEmpty()) {
            alreadyRegisteredVehicle.setVehicleName(vehicle.getVehicleName());
        }
        if (!vehicle.getVehicleDetails().isEmpty()) {
            alreadyRegisteredVehicle.setVehicleDetails(vehicle.getVehicleDetails());
        }
        return iVehicleRepository.save(alreadyRegisteredVehicle);
    } else {
        throw new VehicleNotFoundException();
    }
}
*/
}
