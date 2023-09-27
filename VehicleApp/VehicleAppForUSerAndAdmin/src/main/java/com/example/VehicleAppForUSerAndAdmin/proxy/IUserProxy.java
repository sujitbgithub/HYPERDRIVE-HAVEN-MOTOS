package com.example.VehicleAppForUSerAndAdmin.proxy;


import com.example.VehicleAppForUSerAndAdmin.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "UserAuthenticationService" , url = "http://localhost:9001")
public interface IUserProxy {

    @PostMapping("api/user-auth/v1/register")
    public ResponseEntity<?> addUser(@RequestBody User user);
}
