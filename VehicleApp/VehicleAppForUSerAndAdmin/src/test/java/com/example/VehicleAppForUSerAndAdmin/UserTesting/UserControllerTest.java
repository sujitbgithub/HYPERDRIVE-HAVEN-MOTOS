package com.example.VehicleAppForUSerAndAdmin.UserTesting;

import com.example.VehicleAppForUSerAndAdmin.controller.UserController;
import com.example.VehicleAppForUSerAndAdmin.domain.User;
import com.example.VehicleAppForUSerAndAdmin.domain.Vehicle;
import com.example.VehicleAppForUSerAndAdmin.exception.CartEmptyException;
import com.example.VehicleAppForUSerAndAdmin.exception.UserAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.exception.UserNotFoundException;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.service.UserService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private Claims claims;

    @Test
    void testRegisterUser_Success() throws UserAlreadyExistsException {
        String emailId = "test@example.com";
        List<Vehicle> cart = new ArrayList<>();
//        cart.add(new Vehicle("vehicleId1", "Car","Sports car"));
//        cart.add(new Vehicle("vehicleId2", "Scorpio","SUV"));
        User user = new User(emailId, "password","Test1User",cart);
        System.out.println(user);

        when(userService.registerUser(user)).thenReturn(user);

        ResponseEntity<?> responseEntity = userController.registerUser(user);

        verify(userService, times(1)).registerUser(user);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    void testAddVehicleToCart_Success() throws UserNotFoundException, CartEmptyException, VehicleAlreadyExistsException {
        Vehicle vehicle = new Vehicle("Vehicle123", "Car","SUV");
        String emailId = "test@example.com";
        User updatedUser = new User(emailId, "password");
        when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer Token");
        when(httpServletRequest.getAttribute("claims")).thenReturn(claims);
        when(claims.getSubject()).thenReturn(emailId);
        when(userService.addVehicleToCart(emailId, vehicle)).thenReturn(updatedUser);

        ResponseEntity<?> responseEntity = userController.addVehicleToCart(vehicle, httpServletRequest);

        verify(userService, times(1)).addVehicleToCart(emailId, vehicle);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(updatedUser, responseEntity.getBody());
    }

    @Test
    void testGetAllVehicles_Success() throws UserNotFoundException, CartEmptyException {
        String emailId = "test@example.com";
        List<Vehicle> cart = new ArrayList<>();
        when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer Token");
        when(httpServletRequest.getAttribute("claims")).thenReturn(claims);
        when(claims.getSubject()).thenReturn(emailId);
        when(userService.getAllVehicles(emailId)).thenReturn(cart);

        ResponseEntity<?> responseEntity = userController.getAllVehicles(httpServletRequest);

        verify(userService, times(1)).getAllVehicles(emailId);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cart, responseEntity.getBody());
    }

    @Test
    void testDeleteVehicleFromCart_Success() throws UserNotFoundException {
        String emailId = "test@example.com";
        String vehicleId = "Vehicle123";
        List<Vehicle> cart = new ArrayList<>();
        when(httpServletRequest.getAttribute("claims")).thenReturn(claims);
        when(claims.getSubject()).thenReturn(emailId);
        when(userService.deleteVehicleFromCart(emailId, vehicleId)).thenReturn(cart);

        ResponseEntity<?> responseEntity = userController.deleteVehicleFromCart(vehicleId, httpServletRequest);

        verify(userService, times(1)).deleteVehicleFromCart(emailId, vehicleId);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cart, responseEntity.getBody());
    }
}
