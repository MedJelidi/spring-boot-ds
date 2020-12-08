package com.restaurant.springbootds.services;

import com.restaurant.springbootds.models.MetEntity;
import com.restaurant.springbootds.models.TicketEntity;
import com.restaurant.springbootds.models.Type;
import com.restaurant.springbootds.repositories.MetRepository;
import com.restaurant.springbootds.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MetServiceImpl implements MetService {

    private MetRepository metRepository;
    private TicketRepository ticketRepository;

    @Override
    public MetEntity createMet(MetEntity met) {
        return this.metRepository.save(met);
    }

    @Override
    public List<MetEntity> readMets() {
        return this.metRepository.findAll();
    }

    @Override
    public MetEntity getMetByNom(String nom) {
        MetEntity met = this.metRepository.findById(nom).orElse(null);
        if (met != null) {
            return met;
        }
        throw new NoSuchElementException("Met named '" + nom + "' does not exist.");
    }

    @Override
    public MetEntity updateMet(String nom, MetEntity met) {
        Optional<MetEntity> theMet = this.metRepository.findById(nom);
        if (theMet.isPresent()) {
            MetEntity oldMet = theMet.get();
            if (met.getNom() != null)
                oldMet.setNom(met.getNom());
            if (met.getPrix() != 0)
                oldMet.setPrix(met.getPrix());
            if (met.getType() != null)
                oldMet.setType(met.getType());
            return this.metRepository.save(oldMet);
        }
        throw new NoSuchElementException("Met does not exist.");
    }

    @Override
    public MetEntity deleteMet(String nom) {
        Optional<MetEntity> theMet = this.metRepository.findById(nom);
        if (theMet.isPresent()) {
            MetEntity met = theMet.get();
            this.metRepository.deleteById(nom);
            return met;
        }
        throw new NoSuchElementException("Met does not exist.");
    }

    @Override
    public String mostBookedPlatInPeriod(LocalDate beginDate, LocalDate endDate) {
        List<MetEntity> mets = this.metRepository.findAll()
                .stream()
                .filter(m -> m.getType() == Type.Plat)
                .collect(Collectors.toList());

        Map<String, Long> map = new HashMap<>();
        mets.forEach(m -> {
            Long nTickets = m.getTickets().stream().filter(t -> t.getDate().isAfter(beginDate) && t.getDate().isBefore(endDate)).count();
            map.put(m.getNom(), nTickets);
        });

        return map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
