package com.example.VehicleAppForUSerAndAdmin.service;

import com.example.VehicleAppForUSerAndAdmin.domain.User;
import com.example.VehicleAppForUSerAndAdmin.domain.Vehicle;
import com.example.VehicleAppForUSerAndAdmin.exception.CartEmptyException;
import com.example.VehicleAppForUSerAndAdmin.exception.UserAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.exception.UserNotFoundException;
import com.example.VehicleAppForUSerAndAdmin.exception.VehicleAlreadyExistsException;
import com.example.VehicleAppForUSerAndAdmin.proxy.IUserProxy;
import com.example.VehicleAppForUSerAndAdmin.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Component
public class UserService implements IUserService{

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IUserProxy iUserProxy;

    public UserService(){}
    @Autowired
    public UserService(IUserProxy  iUserProxy, IUserRepository iUserRepository){
        this.iUserProxy=iUserProxy;

        this.iUserRepository=iUserRepository;

    }
    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {

        if (iUserRepository.findById(user.getEmailId()).isPresent())
            {
                    throw new UserAlreadyExistsException();
            }
        User addUser= iUserRepository.save(user);
        if (!addUser.getEmailId().isEmpty())
            {
                ResponseEntity userAuthApp=iUserProxy.addUser(user);
                System.out.println(userAuthApp);
            }
        return addUser;
    }

    @Override
    public User addVehicleToCart(String emailId, Vehicle vehicle) throws VehicleAlreadyExistsException, UserNotFoundException {
        Optional<User> optionalUser = iUserRepository.findById(emailId);
        System.out.println(optionalUser);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();
        System.out.println("User before adding vehicle: " + user);

        List<Vehicle> cart = user.getCart();
        if (cart == null) {
            cart = new ArrayList<>();
        }
        cart.add(vehicle);

        user.setCart(cart);
        System.out.println("User after adding vehicle: " + user);

        return iUserRepository.save(user);

    }

    @Override
    public List<Vehicle> getAllVehicles(String emailId) throws UserNotFoundException, CartEmptyException {
        Optional<User> optionalUser = iUserRepository.findById(emailId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();
        List<Vehicle> cart = user.getCart();

        if (cart == null || cart.isEmpty()) {

            throw new CartEmptyException();
        }

        return cart;
    }

    @Override
    public List<Vehicle> deleteVehicleFromCart(String emailId, String vehicleId) throws UserNotFoundException {
        System.out.println("Program traverses till here");
        User user = iUserRepository.findById(emailId).orElseThrow(UserNotFoundException ::new);
        System.out.println("Program traverses till here tooo");
        List<Vehicle> cart= user.getCart();
        //System.out.println(cart);

       // user.getCart().remove(vehicleId);
        String idToBeRemoved=vehicleId;
        System.out.println("******************************************");
        System.out.println(idToBeRemoved);
        System.out.println(vehicleId);
        Vehicle vehicleToBeRemoved=null;
        Vehicle vehicleToBeSaved=null;

        for (Vehicle vehicle: cart){
            System.out.println("Entry point : "+vehicle.getVehicleId());
                if (vehicle.getVehicleId().matches(idToBeRemoved)){
                    System.out.println("Medium  :->");
                    vehicleToBeRemoved=vehicle;
                    System.out.println("This vehicle is to be removed: "+vehicleToBeRemoved);
                }
             //   cart.add(vehicle.getVehicleId());
//                else if (vehicle.getVehicleId()!=idToBeRemoved){
//                    vehicleToBeSaved=vehicle;
//                    cart.add(vehicleToBeSaved);
//                }
        }

        if (vehicleToBeRemoved!=null){
            System.out.println("****************VEHICLE REMOVAL**************");
                    user.getCart().remove(vehicleToBeRemoved);
                    //user.setCart(vehicleToBeRemoved);

        }

       // cart.remove(vehicleId);
        System.out.println(cart);
        iUserRepository.save(user);
       // cart.removeIf(vehicle -> vehicle.getVehicleId().equals(vehicleId));
        System.out.println();


        return cart;
    }
}
