package com.example.UserAuthenticationVA.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class User {

    @Id
    private String emailId;
    private String password;
   // private String role;
}
