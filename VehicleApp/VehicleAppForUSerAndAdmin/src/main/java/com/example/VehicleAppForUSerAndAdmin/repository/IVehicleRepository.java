package com.example.VehicleAppForUSerAndAdmin.repository;

import com.example.VehicleAppForUSerAndAdmin.domain.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IVehicleRepository extends MongoRepository<Vehicle, String> {
}
