package com.restaurant.springbootds.services;

import com.restaurant.springbootds.models.ClientEntity;

import java.time.DayOfWeek;
import java.util.List;

public interface ClientService {
    ClientEntity createClient(ClientEntity client);

    List<ClientEntity> readClients();

    ClientEntity getClientByID(Long id);

    ClientEntity updateClient(Long id, ClientEntity client);

    ClientEntity deleteClient(Long id);

    ClientEntity mostLoyalClient();

    DayOfWeek mostBookedWeekDayForClient(Long id);
}
