package com.restaurant.springbootds.repositories;

import com.restaurant.springbootds.models.MetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetRepository extends JpaRepository<MetEntity, String> {
}
