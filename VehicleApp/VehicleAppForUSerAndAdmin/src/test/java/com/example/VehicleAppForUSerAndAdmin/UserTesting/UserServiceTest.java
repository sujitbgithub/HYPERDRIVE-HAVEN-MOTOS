package com.example.VehicleAppForUSerAndAdmin.UserTesting;

import com.example.VehicleAppForUSerAndAdmin.domain.User;
import com.example.VehicleAppForUSerAndAdmin.domain.Vehicle;
import com.example.VehicleAppForUSerAndAdmin.exception.CartEmptyException;
import com.example.VehicleAppForUSerAndAdmin.exception.UserAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.exception.UserNotFoundException;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.proxy.IUserProxy;
import com.example.VehicleAppForUSerAndAdmin.repository.IUserRepository;
import com.example.VehicleAppForUSerAndAdmin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserProxy userProxy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() throws UserAlreadyExistsException {
        String emailId = "test@example.com";
        List<Vehicle> cart = new ArrayList<>();
        cart.add(new Vehicle("vehicleId1", "Car","Sports car"));
        cart.add(new Vehicle("vehicleId2", "Scorpio","SUV"));
        User user = new User(emailId, "password","Test1User",cart);

       // user.setCart(cart);
        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);
        System.out.println("***************TEST PArt**********************");
        System.out.println(user);
        ResponseEntity userAuthResponse = ResponseEntity.ok().build();
        User result = userService.registerUser(user);
        when(userProxy.addUser(user)).thenReturn(userAuthResponse);
        System.out.println(userAuthResponse);


        System.out.println(result);

        verify(userRepository, times(1)).findById(user.getEmailId());
        verify(userRepository, times(1)).save(user);
        verify(userProxy, times(1)).addUser(user);

        assertNotNull(result);
        assertEquals(user.getEmailId(), result.getEmailId());
    }

    @Test
    void testAddVehicleToCart_Success() throws VehicleAlreadyExistsException, UserNotFoundException {
        String emailId = "test@example.com";
        User user = new User(emailId, "password");
        Vehicle vehicle = new Vehicle("vehicleId", "Car","Sedan");

        when(userRepository.findById(emailId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addVehicleToCart(emailId, vehicle);

        verify(userRepository, times(1)).findById(emailId);
        verify(userRepository, times(1)).save(user);

        assertNotNull(result);
        assertTrue(result.getCart().contains(vehicle));
    }

    @Test
    void testGetAllVehicles_Success() throws UserNotFoundException, CartEmptyException {
        String emailId = "test@example.com";
        User user = new User(emailId, "password");
        List<Vehicle> cart = new ArrayList<>();
        cart.add(new Vehicle("vehicleId1", "Car","Sports car"));
        cart.add(new Vehicle("vehicleId2", "Scorpio","SUV"));
        user.setCart(cart);

        when(userRepository.findById(emailId)).thenReturn(Optional.of(user));

        List<Vehicle> result = userService.getAllVehicles(emailId);

        verify(userRepository, times(1)).findById(emailId);

        assertNotNull(result);
        assertEquals(cart.size(), result.size());
    }

    @Test
    void testDeleteVehicleFromCart_Success() throws UserNotFoundException {
        String emailId = "test@example.com";
        String vehicleId = "vehicleId1";
        User user = new User(emailId, "password");
        Vehicle vehicle1 = new Vehicle("vehicleId1", "Car","Sports car");
        Vehicle vehicle2 = new Vehicle("vehicleId2", "Scorpio","SUV");
        List<Vehicle> cart = new ArrayList<>();
        cart.add(vehicle1);
        cart.add(vehicle2);
        user.setCart(cart);

        when(userRepository.findById(emailId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        System.out.println("******************Testing Part***********************");
        System.out.println(cart.size());
        System.out.println("*****************************************");
        List<Vehicle> result = userService.deleteVehicleFromCart(emailId, vehicleId);

        verify(userRepository, times(1)).findById(emailId);
        verify(userRepository, times(1)).save(user);

        assertNotNull(result);
        assertEquals(cart.size() , result.size());
        System.out.println(cart.size());
        System.out.println(result.size());
        assertFalse(result.contains(vehicle1));
    }
}
