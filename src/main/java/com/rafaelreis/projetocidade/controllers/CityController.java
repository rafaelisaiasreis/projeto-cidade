package com.rafaelreis.projetocidade.controllers;

import com.rafaelreis.projetocidade.model.repositories.CityRepository;
import com.rafaelreis.projetocidade.model.services.LoadTableCityFromCSVFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    @Autowired
    private CityRepository cidadeRepository;
    @Autowired
    private LoadTableCityFromCSVFile loadTableCityFromCSVFile;

    @GetMapping("/load")
    public ResponseEntity<?> loadTableCityFromCsvFile(){
        loadTableCityFromCSVFile.load();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
