package com.restaurant.springbootds.repositories;

import com.restaurant.springbootds.models.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
}
