package com.bullish.electronicStore.service;

import com.bullish.electronicStore.model.User;
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
            throw new CustomException(String.format("User with email %s already exists", newUser.getEmail()));
        }
        return userRepository.save(newUser);
    }

    public void deleteUserById(int id) throws CustomException {
        if (!userRepository.existsById(id)) {
            throw new CustomException(String.format("User %s does not exist", id));
        }
        userRepository.deleteById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
