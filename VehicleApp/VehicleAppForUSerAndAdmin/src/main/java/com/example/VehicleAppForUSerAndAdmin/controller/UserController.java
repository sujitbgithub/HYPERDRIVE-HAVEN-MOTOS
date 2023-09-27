package com.example.VehicleAppForUSerAndAdmin.controller;


import com.example.VehicleAppForUSerAndAdmin.domain.User;
import com.example.VehicleAppForUSerAndAdmin.domain.Vehicle;
import com.example.VehicleAppForUSerAndAdmin.exception.CartEmptyException;
import com.example.VehicleAppForUSerAndAdmin.exception.UserAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.exception.UserNotFoundException;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.service.IUserService;
import com.example.VehicleAppForUSerAndAdmin.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user-vehicle/v1")
public class UserController {

    private UserService userService;

    private ResponseEntity<?> responseEntity;

    @Autowired
    public UserController(UserService userService){
            this.userService=userService;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody User user ) throws UserAlreadyExistsException{

            try {
                    User newUser = userService.registerUser(user);
                    if (newUser == null){
                        responseEntity=new ResponseEntity<>("Error While Registration", HttpStatus.EXPECTATION_FAILED);
                    }
                    else{
                         responseEntity=new ResponseEntity<>(newUser,HttpStatus.OK);
                    }
            }
            catch (UserAlreadyExistsException userAlreadyExistsException){
                    throw new UserAlreadyExistsException();
            }
            catch (Exception exception){
                System.out.println(exception);
            }

            return responseEntity;
    }

    @PostMapping("/user/addToCart")
    public ResponseEntity<?> addVehicleToCart(@RequestBody Vehicle vehicle, HttpServletRequest httpServletRequest) throws UserNotFoundException, CartEmptyException, VehicleAlreadyExistsException {
            try {

                System.out.println("Header ::-> "+ httpServletRequest.getHeader("Authorization"));
                Claims claims= (Claims) httpServletRequest.getAttribute("claims");
                System.out.println("*******************Claims Email********************");
                String emailId= claims.getSubject();
                System.out.println("*********************TOKEN***********************");
                System.out.println(claims);
                System.out.println("Email :-> "+ emailId);
                User updateUserCart= userService.addVehicleToCart(emailId,vehicle);
                return ResponseEntity.status(HttpStatus.CREATED).body(updateUserCart);
            }
            catch (UserNotFoundException userNotFoundException){
                throw new UserNotFoundException();
            }
            catch ( VehicleAlreadyExistsException vehicleAlreadyExistsException){
                throw new VehicleAlreadyExistsException();
            }
    }

    @GetMapping("/user/getCartItems")
    public ResponseEntity<?> getAllVehicles(HttpServletRequest httpServletRequest) throws UserNotFoundException{
        try{
            System.out.println("header :->" +httpServletRequest.getHeader("Authorization"));
            Claims claims = (Claims) httpServletRequest.getAttribute("claims");
            System.out.println("email from claims :-> " + claims.getSubject());
            String emailId = claims.getSubject();
            System.out.println("email :-> "+emailId);
            responseEntity = new ResponseEntity<>(userService.getAllVehicles(emailId), HttpStatus.OK);
        }catch(UserNotFoundException | CartEmptyException exceptions)
        {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @DeleteMapping("/user/cart/{vehicleId}")
    public ResponseEntity<?> deleteVehicleFromCart(@PathVariable String vehicleId, HttpServletRequest httpServletRequest) throws UserNotFoundException{
        Claims claims = (Claims) httpServletRequest.getAttribute("claims");
        String emailId = claims.getSubject();
        System.out.println("email = "+emailId);
        System.out.println("Vehicle ID :-> "+ vehicleId);
        try {
            responseEntity = new ResponseEntity<>(userService.deleteVehicleFromCart(emailId,vehicleId), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
