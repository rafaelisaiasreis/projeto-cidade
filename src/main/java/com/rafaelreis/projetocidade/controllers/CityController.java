package com.rafaelreis.projetocidade.controllers;

import com.rafaelreis.projetocidade.model.DTO.CityDTO;
import com.rafaelreis.projetocidade.model.entities.City;
import com.rafaelreis.projetocidade.model.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private  CityService cityService;

    @GetMapping("/{ibgeId}")
    public ResponseEntity<City> findCityByIbgeId(@PathVariable Long ibgeId){
        City cityById = cityService.findCityByIbgeId(ibgeId);
        return new ResponseEntity<City>(cityById, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAllCapitalsOrderByName(@RequestParam(name = "capital") Boolean capital){
        List<City> allCapitalsOrderByName =
                cityService.findAllCapitalsOrderByName(capital);
        return new ResponseEntity<>(allCapitalsOrderByName, HttpStatus.OK);
    }

    @GetMapping("/state")
    public ResponseEntity<?> findCitiesByUF(@RequestParam(name = "uf") String uf){
        List<City> allCapitalsOrderByName =
                cityService.findAllCitiesByUF(uf);
        return new ResponseEntity<>(allCapitalsOrderByName, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<City> addNewCity(@RequestBody CityDTO newCity){
        City city = cityService.addNewCity(newCity);
        return new ResponseEntity<City>(city, HttpStatus.CREATED);
    }

    @DeleteMapping("/{ibgeId}")
    public ResponseEntity<?> deleteCityByIbgeId(@PathVariable Long ibgeId){
        cityService.deleteCityByIbgeId(ibgeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
