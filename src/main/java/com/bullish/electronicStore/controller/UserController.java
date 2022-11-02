package com.bullish.electronicStore.controller;

import com.bullish.electronicStore.common.ApiResponse;
import com.bullish.electronicStore.entity.Product;
import com.bullish.electronicStore.entity.User;
import com.bullish.electronicStore.service.ProductService;
import com.bullish.electronicStore.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/users", produces="application/json")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public List<User> getUsers() {
        List<User> body = userService.listAllUsers();
        //String json = new Gson().toJson(body);
        //System.out.println("body" + json);
        return body;
    }

    @PostMapping("/add")
    public User addUsers(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping("/delete/{email}")
    public void deleteUsers(@PathVariable String email) {
        userService.deleteUserByEmail(email);
    }

}
