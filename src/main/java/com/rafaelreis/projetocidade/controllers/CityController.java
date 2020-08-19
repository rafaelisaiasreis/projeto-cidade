package com.rafaelreis.projetocidade.controllers;

import com.rafaelreis.projetocidade.model.entities.City;
import com.rafaelreis.projetocidade.model.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private  CityService cityService;

    @GetMapping("/{id}")
    public ResponseEntity<City> findCityById(@PathVariable Long id){
        City cityById = cityService.findCityById(id);
        return new ResponseEntity<City>(cityById, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<City> addNewCity(City newCity){
        City city = cityService.addNewCity(newCity);
        return new ResponseEntity<City>(city, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCityById(@PathVariable Long id){
        cityService.deleteCityById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
