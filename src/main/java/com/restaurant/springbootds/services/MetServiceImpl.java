package com.restaurant.springbootds.services;

import com.restaurant.springbootds.models.MetEntity;
import com.restaurant.springbootds.repositories.MetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MetServiceImpl implements MetService {

    private MetRepository metRepository;

    @Override
    public MetEntity createMet(MetEntity met) {
        return this.metRepository.save(met);
    }

    @Override
    public List<MetEntity> readMets() {
        return this.metRepository.findAll();
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
}
