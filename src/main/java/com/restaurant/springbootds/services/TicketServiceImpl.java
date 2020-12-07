package com.restaurant.springbootds.services;

import com.restaurant.springbootds.models.ClientEntity;
import com.restaurant.springbootds.models.MetEntity;
import com.restaurant.springbootds.models.TableEntity;
import com.restaurant.springbootds.models.TicketEntity;
import com.restaurant.springbootds.repositories.ClientRepository;
import com.restaurant.springbootds.repositories.MetRepository;
import com.restaurant.springbootds.repositories.TableRepository;
import com.restaurant.springbootds.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private MetRepository metRepository;
    private ClientRepository clientRepository;
    private TableRepository tableRepository;

    public void checkValidation(TicketEntity ticket) {
        if (ticket.getMets() == null) {
            throw new NoSuchElementException("No mets provided.");
        }
        if (ticket.getTable() == null) {
            throw new NoSuchElementException("No table provided.");
        }
        if (ticket.getClient() == null) {
            throw new NoSuchElementException("No client provided.");
        }

        for (MetEntity met : ticket.getMets()) {
            String metNom = met.getNom();
            if (metNom == null) {
                throw new NoSuchElementException("Please provide 'nom' attribute for all mets.");
            }
            met = metRepository.findById(metNom).orElse(null);
            if (met == null) {
                throw new NoSuchElementException("Met named '" + metNom + "' does not exist.");
            }
        }

        Integer tableNumber = ticket.getTable().getNumero();
        if (tableNumber == null) {
            throw new NoSuchElementException("Please provide 'numero' attribute for the table");
        }
        TableEntity table = tableRepository.findById(tableNumber).orElse(null);
        if (table == null) {
            throw new NoSuchElementException("Table number '" + tableNumber + "' does not exist.");
        }

        Long clientID = ticket.getClient().getId();
        if (clientID == null) {
            throw new NoSuchElementException("Please provide 'id' attribute for the client.");
        }
        ClientEntity client = clientRepository.findById(clientID).orElse(null);
        if (client == null) {
            throw new NoSuchElementException("Client with id '" + clientID + "' does not exist.");
        }
    }

    private void saveMets(TicketEntity newTicket) {
        List<TicketEntity> tickets;
        for (MetEntity met : newTicket.getMets()) {
            met = metRepository.findById(met.getNom()).orElse(null);
            if (met.getTickets() != null) {
                tickets = met.getTickets();
            } else {
                tickets = new ArrayList<>();
            }
            tickets.add(newTicket);
            met.setTickets(tickets);
            this.metRepository.save(met);
        }
    }

    private void saveTable(TicketEntity newTicket) {
        TableEntity table = tableRepository.findById(newTicket.getTable().getNumero()).orElse(null);
        List<TicketEntity> tableTickets = new ArrayList<>();
        if (table.getTickets() != null) {
            tableTickets = table.getTickets();
        }
        tableTickets.add(newTicket);
        table.setTickets(tableTickets);
        this.tableRepository.save(table);
    }

    private void saveClient(TicketEntity newTicket) {
        ClientEntity client = clientRepository.findById(newTicket.getClient().getId()).orElse(null);
        List<TicketEntity> userTickets = new ArrayList<>();
        if (client.getTickets() != null) {
            userTickets = client.getTickets();
        }
        userTickets.add(newTicket);
        client.setTickets(userTickets);
        this.clientRepository.save(client);
    }

    @Override
    public TicketEntity createTicket(TicketEntity ticket) {
        checkValidation(ticket);
        TicketEntity newTicket = this.ticketRepository.save(ticket);
        saveClient(newTicket);
        saveTable(newTicket);
        saveMets(newTicket);
        return newTicket;
    }

    @Override
    public List<TicketEntity> readTickets() {
        return this.ticketRepository.findAll();
    }

    @Override
    public TicketEntity updateTicket(int numero, TicketEntity newTicket) {
        Optional<TicketEntity> ticket = this.ticketRepository.findById(numero);

        if (ticket.isPresent()) {
            TicketEntity oldTicket = ticket.get();
            mergeTicket(oldTicket, newTicket);

            if (newTicket.getClient() != null) {
                Long clientID = newTicket.getClient().getId();
                if (clientID == null) {
                    throw new NoSuchElementException("Please provide 'id' attribute for the client.");
                }
                ClientEntity newClient = clientRepository.findById(newTicket.getClient().getId()).orElse(null);
                if (newClient == null) {
                    throw new NoSuchElementException("Client with id '" + clientID + "' does not exist.");
                }
                oldTicket.setClient(newClient);
                List<TicketEntity> userTickets = new ArrayList<>();
                if (newClient.getTickets() != null) {
                    userTickets = newClient.getTickets();
                }
                userTickets.add(newTicket);
                userTickets.remove(oldTicket);
                newClient.setTickets(userTickets);
                this.clientRepository.save(newClient);
            }

            if (newTicket.getTable() != null) {
                Integer tableNum = newTicket.getTable().getNumero();
                if (tableNum == null) {
                    throw new NoSuchElementException("Please provide 'numero' attribute for the table.");
                }
                TableEntity newTable = tableRepository.findById(newTicket.getTable().getNumero()).orElse(null);
                if (newTable == null) {
                    throw new NoSuchElementException("Table number '" + tableNum + "' does not exist.");
                }
                oldTicket.setTable(newTable);
                List<TicketEntity> tableTickets = new ArrayList<>();
                if (newTable.getTickets() != null) {
                    tableTickets = newTable.getTickets();
                }
                tableTickets.add(newTicket);
                tableTickets.remove(oldTicket);
                newTable.setTickets(tableTickets);
                this.tableRepository.save(newTable);
            }

            if (newTicket.getMets() != null) {

                List<MetEntity> mets = new ArrayList<>();
                newTicket.getMets().forEach(met -> {
                    met = metRepository.findById(met.getNom()).orElse(null);
                    if (met != null) {
                        mets.add(met);
                    }
                });
                oldTicket.setMets(mets);
                TicketEntity ti = this.ticketRepository.save(oldTicket);

                saveMets(ti);


//                oldTicket.getMets().addAll(newTicket.getMets());
//                TicketEntity ti = this.ticketRepository.save(oldTicket);
//                saveMets(ti);
//                List<MetEntity> newMets = new ArrayList<>();
//                newTicket.getMets().forEach(met -> {
//                    met = metRepository.findById(met.getNom()).orElse(null);
//                    newMets.add(met);
//                });
//                oldTicket.setMets(newMets);
//                TicketEntity tick = ticketRepository.save(oldTicket);
//
//                newTicket.getMets().forEach(met -> {
//                    met = metRepository.findById(met.getNom()).orElse(null);
//                    if (met.getTickets() != null) {
//                        met.getTickets().remove(oldTicket);
//                        metRepository.save(met);
//                    }
//                });
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
