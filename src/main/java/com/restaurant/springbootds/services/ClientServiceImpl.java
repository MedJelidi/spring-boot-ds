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

    @Override
    public ClientEntity createClient(ClientEntity client) {
        return this.clientRepository.save(client);
    }

    @Override
    public List<ClientEntity> readClients() {
        return this.clientRepository.findAll();
    }

    @Override
    public ClientEntity updateClient(Long id, ClientEntity client) {
        Optional<ClientEntity> theClient = this.clientRepository.findById(id);
        if (theClient.isPresent()) {
            ClientEntity oldClient = theClient.get();
            TicketServiceImpl.mergeClient(oldClient, client);
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
        List<TicketEntity> tickets = this.ticketRepository.findAll();
//        Map<Long, Integer> clientScore = new HashMap<Long, Integer>();
//        for (TicketEntity ticket : tickets) {
//            if (ticket.getClient() != null) {
//                if (clientScore.containsKey(ticket.getClient().getId()))
//                    clientScore.replace(ticket.getClient().getId(), clientScore.get(ticket.getClient().getId()), clientScore.get(ticket.getClient().getId()) + 1);
//                else
//                    clientScore.put(ticket.getClient().getId(), 1);
//            }
//        }
//        Long clientID = Collections.max(clientScore.entrySet(), Map.Entry.comparingByValue()).getKey();
//
//        return this.clientRepository.findById(clientID).orElse(null);
        return tickets.stream()
                // summarize Clients
                .collect(Collectors.groupingBy(TicketEntity::getClient, Collectors.counting()))
                // fetch the max entry
                .entrySet().stream().max(Map.Entry.comparingByValue())
                // map to Client
                .map(Map.Entry::getKey).orElse(null);
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
                .map(Map.Entry::getKey).orElse(null);
    }
}
