package com.example.VehicleAppForUSerAndAdmin.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class User {

    @Id
    private String emailId;
    private String password;
    private String userName;

    private List<Vehicle> cart;

    public User(String emailId, String password) {
    }
}
