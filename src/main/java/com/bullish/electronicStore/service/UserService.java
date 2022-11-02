package com.bullish.electronicStore.service;

import com.bullish.electronicStore.model.User;
import com.bullish.electronicStore.enums.UserRoles;
import com.bullish.electronicStore.exception.CustomException;
import com.bullish.electronicStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User newUser) throws CustomException {
        // Check to see if the current email address has already been registered.
        if (userRepository.findByEmail(newUser.getEmail()) != null) {
            // If the email address has been registered then throw an exception.
            throw new CustomException("User already exists");
        }
        return userRepository.save(newUser);
    }

    public void deleteUserByEmail(String email) throws CustomException {
        if (userRepository.findByEmail(email) == null) {
            throw new CustomException("User does not exist");
        }
        userRepository.deleteByEmail(email);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
