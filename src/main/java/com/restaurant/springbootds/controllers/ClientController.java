package com.restaurant.springbootds.controllers;

import com.restaurant.springbootds.models.ClientEntity;
import com.restaurant.springbootds.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
@AllArgsConstructor
public class ClientController {

    private ClientService clientService;

    @PostMapping
    public ClientEntity createClient(@RequestBody ClientEntity client){
        return this.clientService.createClient(client);
    }

    @GetMapping
    public List<ClientEntity> getClients() {
        return this.clientService.readClients();
    }

    @GetMapping("/{id}")
    public ClientEntity getClientByID(@PathVariable("id") Long id) {return this.clientService.getClientByID(id);}

    @PutMapping("/{id}")
    public ClientEntity updateClient(@PathVariable("id") Long id, @RequestBody ClientEntity client) {
        return this.clientService.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    public ClientEntity deleteClient(@PathVariable("id") Long id) {
        return this.clientService.deleteClient(id);
    }

    @GetMapping("/mostLoyal")
    public ClientEntity mostLoyalClient() {
        return this.clientService.mostLoyalClient();
    }

    @GetMapping("/mostBookedWeekDay/{id}")
    public DayOfWeek mostBookedWeekDayForClient(@PathVariable("id") Long id) {
        return this.clientService.mostBookedWeekDayForClient(id);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
