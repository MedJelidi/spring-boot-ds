package com.restaurant.springbootds.controllers;

import com.restaurant.springbootds.models.TicketEntity;
import com.restaurant.springbootds.services.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/ticket")
@AllArgsConstructor
public class TicketController {

    private TicketService ticketService;

    @PostMapping
    public TicketEntity createTicket(@RequestBody TicketEntity ticket){
        return this.ticketService.createTicket(ticket);
    }

    @GetMapping
    public List<TicketEntity> getTickets() {
        return this.ticketService.readTickets();
    }

    @GetMapping("/{numero}")
    public TicketEntity getTicketByNumero(@PathVariable("numero") int numero) {
        return this.ticketService.getTicketByNum(numero);
    }

    @PutMapping("/{numero}")
    public TicketEntity updateTicket(@PathVariable("numero") int numero, @RequestBody TicketEntity ticket) {
        return this.ticketService.updateTicket(numero, ticket);
    }

    @DeleteMapping("/{numero}")
    public TicketEntity deleteTicket(@PathVariable("numero") int numero) {
        return this.ticketService.deleteTicket(numero);
    }

    @GetMapping("/periodRevenue/{beginDate}/{endDate}")
    public float periodRevenue(@PathVariable("beginDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
                               @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return this.ticketService.periodRevenue(beginDate, endDate);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
