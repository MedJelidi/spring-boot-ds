package com.restaurant.springbootds.repositories;

import com.restaurant.springbootds.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
