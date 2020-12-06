package com.restaurant.springbootds.services;

import com.restaurant.springbootds.models.ClientEntity;
import com.restaurant.springbootds.models.MetEntity;
import com.restaurant.springbootds.models.TableEntity;
import com.restaurant.springbootds.models.TicketEntity;
import com.restaurant.springbootds.repositories.MetRepository;
import com.restaurant.springbootds.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private MetRepository metRepository;

    @Override
    public TicketEntity createTicket(TicketEntity ticket) {
        TicketEntity newTicket = this.ticketRepository.save(ticket);
        List<TicketEntity> tickets;
        for (MetEntity met : ticket.getMets()) {
            if (met.getTickets() != null) {
                tickets = met.getTickets();
            } else {
                tickets = new ArrayList<>();
            }
            tickets.add(newTicket);
            met.setTickets(tickets);
            this.metRepository.save(met);
        }
        return newTicket;
    }

    @Override
    public List<TicketEntity> readTickets() {
        return this.ticketRepository.findAll();
    }

    @Override
    public TicketEntity updateTicket(int numero, TicketEntity ticket) {
        Optional<TicketEntity> theTicket = this.ticketRepository.findById(numero);

        if (theTicket.isPresent()) {

            TicketEntity oldTicket = theTicket.get();
            mergeTicket(oldTicket, ticket);


            ClientEntity oldClient = oldTicket.getClient();
            ClientEntity newClient = ticket.getClient();
            if (newClient != null)
                mergeClient(oldClient, newClient);

            TableEntity oldTable = oldTicket.getTable();
            TableEntity newTable = ticket.getTable();
            if (newTable != null) {
                mergeTable(oldTable, newTable);
            }

            List<MetEntity> oldMets = oldTicket.getMets();
            List<MetEntity> newMets = ticket.getMets();
            if (newMets != null) {
               mergeMets(oldMets, newMets);
            }

            return this.ticketRepository.save(oldTicket);
        }
        throw new NoSuchElementException("Met does not exist.");
    }

    @Override
    public TicketEntity deleteTicket(int numero) {
        Optional<TicketEntity> theTicket = this.ticketRepository.findById(numero);
        if (theTicket.isPresent()) {
            TicketEntity ticket = theTicket.get();
            this.ticketRepository.deleteById(numero);
            return ticket;
        }
        throw new NoSuchElementException("Ticket does not exist.");
    }

    @Override
    public float periodRevenue(LocalDate beginDate, LocalDate endDate) {
        return this.ticketRepository.findAll()
                .stream()
                .filter(t -> t.getDate().isAfter(beginDate) && t.getDate().isBefore(endDate))
                .map(TicketEntity::getAddition)
                .reduce((float) 0, Float::sum);
    }

    static void mergeClient(ClientEntity oldClient, ClientEntity newClient) {
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

    public void mergeTable(TableEntity oldTable, TableEntity newTable) {
        if (newTable.getNbCouvert() != null)
            oldTable.setNbCouvert(newTable.getNbCouvert());
        if (newTable.getSupplement() != 0)
            oldTable.setSupplement(newTable.getSupplement());
        if (newTable.getType() != null)
            oldTable.setType(newTable.getType());
    }

    public void mergeTicket(TicketEntity oldTicket, TicketEntity newTicket) {
        if (newTicket.getAddition() != 0)
            oldTicket.setAddition(newTicket.getAddition());
        if (newTicket.getDate() != null)
            oldTicket.setDate(newTicket.getDate());
        if (newTicket.getNbCouvert() != null)
            oldTicket.setNbCouvert(newTicket.getNbCouvert());
    }

    public void mergeMets(List<MetEntity> oldMets, List<MetEntity> newMets) {
        for (MetEntity newMet : newMets) {
            for (MetEntity oldMet : oldMets) {
                if (oldMet.getNom().equals(newMet.getNom())) {
                    if (newMet.getPrix() != 0)
                        oldMet.setPrix(newMet.getPrix());
                    break;
                }
            }
        }
    }
}
