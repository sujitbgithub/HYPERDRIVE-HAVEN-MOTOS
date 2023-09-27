package com.example.UserAuthenticationVA;

import com.example.UserAuthenticationVA.domain.User;
import com.example.UserAuthenticationVA.repository.IUserRepository;
import com.example.UserAuthenticationVA.exception.UserNotFoundException;
import com.example.UserAuthenticationVA.exception.UserAlreadyExistsException;
import com.example.UserAuthenticationVA.service.UserServiceIMPL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceIMPL userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser_Success() throws UserAlreadyExistsException {
        User user = new User("test@example.com", "password");
        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User addedUser = userService.addUser(user);

        assertNotNull(addedUser);
        assertEquals(user.getEmailId(), addedUser.getEmailId());
        assertEquals(user.getPassword(), addedUser.getPassword());
        //assertEquals(user.getUserName(), addedUser.getUserName());

        verify(userRepository, times(1)).findById(user.getEmailId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testAddUser_UserAlreadyExists() {
        User user = new User("test@example.com", "password");
        when(userRepository.findById(user.getEmailId())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> userService.addUser(user));

        verify(userRepository, times(1)).findById(user.getEmailId());
        verify(userRepository, never()).save(user);
    }

    @Test
    void testFindByEmailAndPassword_Success() throws UserNotFoundException {
        User user = new User("test@example.com", "password");
        when(userRepository.findByEmailIdAndPassword(user.getEmailId(), user.getPassword())).thenReturn(user);

        User foundUser = userService.findByEmailIdAndPassword(user.getEmailId(), user.getPassword());

        assertNotNull(foundUser);
        assertEquals(user.getEmailId(), foundUser.getEmailId());
        assertEquals(user.getPassword(), foundUser.getPassword());
        //assertEquals(user.getUserName(), foundUser.getUserName());

        verify(userRepository, times(1)).findByEmailIdAndPassword(user.getEmailId(), user.getPassword());
    }

    @Test
    void testFindByEmailAndPassword_UserNotFound() {
        when(userRepository.findByEmailIdAndPassword("nonexistent@example.com", "password")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.findByEmailIdAndPassword("nonexistent@example.com", "password"));

        verify(userRepository, times(1)).findByEmailIdAndPassword("nonexistent@example.com", "password");
    }
}
