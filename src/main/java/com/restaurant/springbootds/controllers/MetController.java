package com.restaurant.springbootds.controllers;

import com.restaurant.springbootds.models.MetEntity;
import com.restaurant.springbootds.services.MetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/met")
@AllArgsConstructor
public class MetController {

    private MetService metService;

    @PostMapping
    public MetEntity createMet(@RequestBody MetEntity met){
        return this.metService.createMet(met);
    }

    @GetMapping
    public List<MetEntity> getMets() {
        return this.metService.readMets();
    }

    @PutMapping("/{nom}")
    public MetEntity updateMet(@PathVariable("nom") String nom, @RequestBody MetEntity met) {
        return this.metService.updateMet(nom, met);
    }

    @DeleteMapping("/{numero}")
    public MetEntity deleteMet(@PathVariable("nom") String nom) {
        return this.metService.deleteMet(nom);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
