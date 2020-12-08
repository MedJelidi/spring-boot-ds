package com.restaurant.springbootds.services;

import com.restaurant.springbootds.models.MetEntity;

import java.util.List;

public interface MetService {
    MetEntity createMet(MetEntity met);
    List<MetEntity> readMets();
    MetEntity getMetByNom(String nom);
    MetEntity updateMet(String nom, MetEntity met);
    MetEntity deleteMet(String met);
}
