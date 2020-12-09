package com.restaurant.springbootds.services;

import com.restaurant.springbootds.models.UserEntity;

public interface UserService {
    UserEntity createUser(UserEntity user);
    UserEntity getUserByUsername(String username);
}
