package com.restaurant.springbootds.services;

import com.restaurant.springbootds.models.UserEntity;
import com.restaurant.springbootds.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        UserEntity user = userRepository.findById(username).orElse(null);
        if (user == null)
            throw new NoSuchElementException("No user with username = " + username);
        return user;
    }
}
