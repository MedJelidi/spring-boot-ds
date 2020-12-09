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
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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

        verifyMets(ticket);

        verifyTable(ticket);

        verifyClient(ticket);
    }

    private void verifyClient(TicketEntity newTicket) {
        Long clientID = newTicket.getClient().getId();
        if (clientID == null) {
            throw new NoSuchElementException("Please provide 'id' attribute for the client.");
        }
        ClientEntity newClient = clientRepository.findById(clientID).orElse(null);
        if (newClient == null) {
            throw new NoSuchElementException("Client with id '" + clientID + "' does not exist.");
        }
    }

    private void verifyTable(TicketEntity newTicket) {
        Integer tableNum = newTicket.getTable().getNumero();
        if (tableNum == null) {
            throw new NoSuchElementException("Please provide 'numero' attribute for the table.");
        }
        TableEntity newTable = tableRepository.findById(tableNum).orElse(null);
        if (newTable == null) {
            throw new NoSuchElementException("Table number '" + tableNum + "' does not exist.");
        }
    }

    private void verifyMets(TicketEntity newTicket) {
        for (MetEntity met : newTicket.getMets()) {
            String metNom = met.getNom();
            if (metNom == null) {
                throw new NoSuchElementException("Please provide 'nom' attribute for all mets.");
            }
            met = metRepository.findById(metNom).orElse(null);
            if (met == null) {
                throw new NoSuchElementException("Met named '" + metNom + "' does not exist.");
            }
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

    public void mergeTicket(TicketEntity oldTicket, TicketEntity newTicket) {
        if (newTicket.getAddition() != 0)
            oldTicket.setAddition(newTicket.getAddition());
        if (newTicket.getDate() != null)
            oldTicket.setDate(newTicket.getDate());
        if (newTicket.getNbCouvert() != null)
            oldTicket.setNbCouvert(newTicket.getNbCouvert());
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
    public TicketEntity getTicketByNum(int numero) {
        TicketEntity ticket = this.ticketRepository.findById(numero).orElse(null);
        if (ticket != null) {
            return ticket;
        }
        throw new NoSuchElementException("Ticket number '" + numero + "' does not exist.");
    }

    @Override
    public TicketEntity updateTicket(int numero, TicketEntity newTicket) {
        Optional<TicketEntity> ticket = this.ticketRepository.findById(numero);

        if (ticket.isPresent()) {
            TicketEntity oldTicket = ticket.get();

            // Merges ticket data
            mergeTicket(oldTicket, newTicket);

            // Updates Client if provided and valid
            if (newTicket.getClient() != null) {
                verifyClient(newTicket);
                ClientEntity newClient = clientRepository.findById(newTicket.getClient().getId()).orElse(null);
                ClientEntity oldClient = clientRepository.findById(oldTicket.getClient().getId()).orElse(null);
                oldTicket.setClient(newClient);
                List<TicketEntity> userTickets = new ArrayList<>();
                if (newClient.getTickets() != null) {
                    userTickets = newClient.getTickets();
                }

                List<TicketEntity> oldUserTickets = new ArrayList<>();
                if (oldClient.getTickets() != null) {
                    oldUserTickets = oldClient.getTickets();
                }
                userTickets.add(newTicket);
                oldUserTickets.remove(oldTicket);
                newClient.setTickets(userTickets);
                oldClient.setTickets(oldUserTickets);
                System.out.println(this.clientRepository.save(newClient));
                System.out.println(this.clientRepository.save(oldClient));
            }

            // Updates Table if provided and valid
            if (newTicket.getTable() != null) {
                verifyTable(newTicket);
                TableEntity newTable = tableRepository.findById(newTicket.getTable().getNumero()).orElse(null);
                TableEntity oldTable = tableRepository.findById(oldTicket.getTable().getNumero()).orElse(null);
                oldTicket.setTable(newTable);
                List<TicketEntity> tableTickets = new ArrayList<>();
                if (newTable.getTickets() != null) {
                    tableTickets = newTable.getTickets();
                }
                List<TicketEntity> oldTableTickets = new ArrayList<>();
                if (oldTable.getTickets() != null) {
                    oldTableTickets = oldTable.getTickets();
                }
                tableTickets.add(newTicket);
                oldTableTickets.remove(oldTicket);
                newTable.setTickets(tableTickets);
                oldTable.setTickets(oldTableTickets);
                System.out.println(this.tableRepository.save(newTable));
                System.out.println(this.tableRepository.save(oldTable));
            }

            // Updates Mets if provided and valid
            if (newTicket.getMets() != null) {
                verifyMets(newTicket);
                List<MetEntity> oldMets = oldTicket.getMets();
                oldMets.forEach(m -> {
                    List<TicketEntity> newTickets = new ArrayList<>();
                    m.getTickets().forEach(t -> {
                        t = ticketRepository.findById(t.getNumero()).orElse(null);
                        if (!t.getNumero().equals(oldTicket.getNumero())) {
                            newTickets.add(t);
                        }
                    });
                    m.setTickets(newTickets);
                    System.out.println(metRepository.save(m));
                });
                oldTicket.getMets().clear();

                List<MetEntity> newMets = new ArrayList<>();
                newTicket.getMets().forEach(m -> {
                    MetEntity met = metRepository.findById(m.getNom()).orElse(null);
                    newMets.add(met);
                });

                oldTicket.setMets(newMets);

                newMets.forEach(m -> {
                    List<TicketEntity> metTickets = new ArrayList<>();
                    if (m.getTickets() != null) {
                        metTickets = m.getTickets();
                    }
                    metTickets.add(oldTicket);
                    m.setTickets(metTickets);
                    this.metRepository.save(m);
                });
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

    @Override
    public Map<String, String> dayWeekMonthRevenue() {

        float sumAddition = this.ticketRepository.findAll()
                .stream()
                .map(TicketEntity::getAddition)
                .reduce((float) 0, Float::sum);

        LocalDate beginDate = this.ticketRepository.findAll()
                .stream()
                .map(TicketEntity::getDate)
                .collect(Collectors.toList())
                .stream()
                .min(LocalDate::compareTo).get();

        LocalDate endDate = this.ticketRepository.findAll()
                .stream()
                .map(TicketEntity::getDate)
                .collect(Collectors.toList())
                .stream()
                .max(LocalDate::compareTo).get();

        long days = ChronoUnit.DAYS.between(beginDate, endDate);
        long weeks = ChronoUnit.WEEKS.between(beginDate, endDate);
        long months = ChronoUnit.MONTHS.between(beginDate, endDate);

        Map<String, String> revenues = new HashMap<>();

        revenues.put("revenuePerDay", days == 0 ? "UNAVAILABLE" : String.valueOf(sumAddition / days));
        revenues.put("revenuePerWeek", weeks == 0 ? "UNAVAILABLE" : String.valueOf(sumAddition / weeks));
        revenues.put("revenuePerMonth", months == 0 ? "UNAVAILABLE" : String.valueOf(sumAddition / months));

        return revenues;
    }

}
