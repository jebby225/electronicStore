package com.bullish.electronicStore.controller;

import com.bullish.electronicStore.model.User;
import com.bullish.electronicStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/users", produces="application/json")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.listAllUsers();
    }

    @PostMapping
    public User addUsers(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping("/id")
    public void deleteUsers(@PathVariable int id) {
        userService.deleteUserById(id);
    }

}
