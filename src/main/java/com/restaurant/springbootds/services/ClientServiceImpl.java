package com.restaurant.springbootds.services;

import com.restaurant.springbootds.models.ClientEntity;
import com.restaurant.springbootds.models.TicketEntity;
import com.restaurant.springbootds.repositories.ClientRepository;
import com.restaurant.springbootds.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private TicketRepository ticketRepository;

    void mergeClient(ClientEntity oldClient, ClientEntity newClient) {
        if (newClient.getNom() != null)
            oldClient.setNom(newClient.getNom());
        if (newClient.getPrenom() != null)
            oldClient.setPrenom(newClient.getPrenom());
        if (newClient.getDateDeNaissance() != null)
            oldClient.setDateDeNaissance(newClient.getDateDeNaissance());
        if (newClient.getCourriel() != null)
            oldClient.setCourriel(newClient.getCourriel());
        if (newClient.getTelephone() != null)
            oldClient.setTelephone(newClient.getTelephone());
    }

    @Override
    public ClientEntity createClient(ClientEntity client) {
        return this.clientRepository.save(client);
    }

    @Override
    public List<ClientEntity> readClients() {
        return this.clientRepository.findAll();
    }

    @Override
    public ClientEntity getClientByID(Long id) {
        ClientEntity client = this.clientRepository.findById(id).orElse(null);
        if (client != null) {
            return client;
        }
        throw new NoSuchElementException("Client with id '" + id + "' does not exist.");
    }

    @Override
    public ClientEntity updateClient(Long id, ClientEntity client) {
        Optional<ClientEntity> theClient = this.clientRepository.findById(id);
        if (theClient.isPresent()) {
            ClientEntity oldClient = theClient.get();
            mergeClient(oldClient, client);
            return this.clientRepository.save(oldClient);
        }
        throw new NoSuchElementException("Client does not exist.");
    }

    @Override
    public ClientEntity deleteClient(Long id) {
        Optional<ClientEntity> theClient = this.clientRepository.findById(id);
        if (theClient.isPresent()) {
            ClientEntity client = theClient.get();
            this.clientRepository.deleteById(id);
            return client;
        }
        throw new NoSuchElementException("Client does not exist.");
    }

    @Override
    public ClientEntity mostLoyalClient() {
        return this.ticketRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(TicketEntity::getClient, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    @Override
    public DayOfWeek mostBookedWeekDayForClient(Long id) {
        return this.ticketRepository.findAll()
                .stream()
                .filter(t -> t.getClient().getId().equals(id))
                .map(t -> t.getDate().getDayOfWeek())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
