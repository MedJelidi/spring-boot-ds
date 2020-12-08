package com.restaurant.springbootds.services;

import com.restaurant.springbootds.models.TicketEntity;

import java.time.LocalDate;
import java.util.List;

public interface TicketService {
    TicketEntity createTicket(TicketEntity ticket);
    List<TicketEntity> readTickets();
    TicketEntity getTicketByNum(int numero);
    TicketEntity updateTicket(int numero, TicketEntity ticket);
    TicketEntity deleteTicket(int numero);
    float periodRevenue(LocalDate beginDate, LocalDate endDate);
}
