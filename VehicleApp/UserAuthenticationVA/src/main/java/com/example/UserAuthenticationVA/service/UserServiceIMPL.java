package com.example.UserAuthenticationVA.service;

import com.example.UserAuthenticationVA.domain.User;
import com.example.UserAuthenticationVA.exception.UserAlreadyExistsException;
import com.example.UserAuthenticationVA.exception.UserNotFoundException;
import com.example.UserAuthenticationVA.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceIMPL implements IUserService{

    private IUserRepository iUserRepository;

    @Autowired
    public UserServiceIMPL(IUserRepository iUserRepository){this.iUserRepository=iUserRepository;}
    @Override
    public User addUser(User user) throws UserAlreadyExistsException {

        if (iUserRepository.findById(user.getEmailId()).isPresent()){
                    throw new UserAlreadyExistsException();
        }
        else{
            return iUserRepository.save(user);
        }

    }

    @Override
    public User findByEmailIdAndPassword(String emailId, String password) throws UserNotFoundException {
        User checkUser= iUserRepository.findByEmailIdAndPassword(emailId,password);

        if (checkUser==null){
                throw new UserNotFoundException();
        }
        else{
            return checkUser;
        }

    }
}
