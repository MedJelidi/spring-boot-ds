package com.restaurant.springbootds.repositories;

import com.restaurant.springbootds.models.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableEntity, Integer> {
}
