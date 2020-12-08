package com.restaurant.springbootds.services;

import com.restaurant.springbootds.models.MetEntity;

import java.time.LocalDate;
import java.util.List;

public interface MetService {
    MetEntity createMet(MetEntity met);
    List<MetEntity> readMets();
    MetEntity getMetByNom(String nom);
    MetEntity updateMet(String nom, MetEntity met);
    MetEntity deleteMet(String met);
    String mostBookedPlatInPeriod(LocalDate beginDate, LocalDate endDate);
}
