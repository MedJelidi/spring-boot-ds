package com.restaurant.springbootds.controllers;

import com.restaurant.springbootds.models.ClientEntity;
import com.restaurant.springbootds.models.UserEntity;
import com.restaurant.springbootds.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user){
        return this.userService.createUser(user);
    }


}
