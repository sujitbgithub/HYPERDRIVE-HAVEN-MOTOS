package com.example.VehicleAppForUSerAndAdmin.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Vehicle {

    @Id
    private String vehicleId;
    private String vehicleName;
    private String vehicleDetails;
}
