package com.restaurant.springbootds.repositories;

import com.restaurant.springbootds.models.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
