package com.example.VehicleAppForUSerAndAdmin.repository;

import com.example.VehicleAppForUSerAndAdmin.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserRepository extends MongoRepository<User, String> {
}
