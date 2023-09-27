package com.example.UserAuthenticationVA.service;

import com.example.UserAuthenticationVA.domain.User;
import com.example.UserAuthenticationVA.exception.UserAlreadyExistsException;
import com.example.UserAuthenticationVA.exception.UserNotFoundException;

public interface IUserService {

    public User addUser(User user) throws UserAlreadyExistsException;

    public User findByEmailIdAndPassword(String emailId, String password) throws UserNotFoundException;
}
