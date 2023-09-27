package com.example.VehicleAppForUSerAndAdmin.UserTesting;

import com.example.VehicleAppForUSerAndAdmin.domain.User;
import com.example.VehicleAppForUSerAndAdmin.domain.Vehicle;
import com.example.VehicleAppForUSerAndAdmin.repository.IUserRepository;
import com.example.VehicleAppForUSerAndAdmin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


//class UserRepositoryTest {
//
//
//
//    @InjectMocks
//    private UserService userService;
//
//    @Mock
//    private IUserRepository iUserRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//    @Test
//    void testFindById_Success() {
//        String emailId = "test@example.com";
//        User user = new User(emailId, "password");
//
//        when(mongoTemplate.findById(emailId, User.class)).thenReturn(user);
//
//        Optional<User> result = userRepository.findById(emailId);
//
//        verify(mongoTemplate, times(1)).findById(emailId, User.class);
//        assertTrue(result.isPresent());
//        assertEquals(emailId, result.get().getEmailId());
//    }
//
//    @Test
//    void testSave_Success() {
//        User user = new User("test@example.com", "password");
//
//        when(mongoTemplate.save(user)).thenReturn(user);
//
//        User savedUser = userRepository.save(user);
//
//        verify(mongoTemplate, times(1)).save(user);
//        assertNotNull(savedUser);
//        assertEquals(user.getEmailId(), savedUser.getEmailId());
//    }
//
//    @Test
//    void testDeleteById_Success() {
//        String emailId = "test@example.com";
//
//        userRepository.deleteById(emailId);
//
//        verify(mongoTemplate, times(1)).remove(eq(Query.query(Criteria.where("emailId").is(emailId))), eq(User.class));
//    }
//
//    // Add test cases for other methods like findAll, deleteAll, etc.
//}
import com.example.VehicleAppForUSerAndAdmin.domain.User;
import com.example.VehicleAppForUSerAndAdmin.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserRepositoryTesting {

    @Mock
    private IUserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        String emailId = "test@example.com";
        List<Vehicle> cart = new ArrayList<>();
        cart.add(new Vehicle("vehicleId1", "Car","Sports car"));
        cart.add(new Vehicle("vehicleId2", "Scorpio","SUV"));
        User user = new User(emailId, "password","Test1User",cart);

        when(userRepository.findById(emailId)).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findById(emailId);

        verify(userRepository, times(1)).findById(emailId);
        assertTrue(result.isPresent());
        System.out.println(result.get().getEmailId());
        System.out.println(user);
        assertEquals(emailId, result.get().getEmailId());
    }

    @Test
    void testSave_Success() {
        User user = new User("test@example.com", "password");

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userRepository.save(user);

        verify(userRepository, times(1)).save(user);
        assertNotNull(savedUser);
        assertEquals(user.getEmailId(), savedUser.getEmailId());
    }


}
