package com.rafaelreis.projetocidade.controllers;

import com.rafaelreis.projetocidade.model.DTO.CitiesPerStateDTO;
import com.rafaelreis.projetocidade.model.DTO.CityDTO;
import com.rafaelreis.projetocidade.model.DTO.RegisterCountDTO;
import com.rafaelreis.projetocidade.model.DTO.StatesWithMostAndLessCitiesDTO;
import com.rafaelreis.projetocidade.model.entities.City;
import com.rafaelreis.projetocidade.repositories.CityRepository;
import com.rafaelreis.projetocidade.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;
    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/{ibgeId}")
    public ResponseEntity<City> getCityByIbgeId(@PathVariable Long ibgeId) {
        Optional<City> cityByIbgeId = cityService.findCityByIbgeId(ibgeId);
        if (cityByIbgeId.isPresent()) {
            return ResponseEntity.ok(cityByIbgeId.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/capitals")
    public ResponseEntity<List<City>> getAllCapitalsOrderByName(
            @RequestParam(name = "capital") Boolean capital) {
        List<City> allCapitalsOrderByName =
                cityService.findAllCapitalsOrderByName(capital);
        return ResponseEntity.ok(allCapitalsOrderByName);
    }

    @GetMapping("/states")
    public ResponseEntity<List<City>> getCitiesByUF(@RequestParam(name = "uf") String uf) {
        List<City> allCapitalsOrderByName =
                cityService.findAllCitiesByUF(uf);
        return ResponseEntity.ok(allCapitalsOrderByName);
    }

    @GetMapping("/states/most-less-cities")
    public ResponseEntity<StatesWithMostAndLessCitiesDTO> getStatesWithMoreAndLessCities() {
        StatesWithMostAndLessCitiesDTO statesWithMostAndLessCities =
                cityService.getStatesWithMostAndLessCities();
        return ResponseEntity.ok(statesWithMostAndLessCities);
    }

    @GetMapping("/per-state")
    public ResponseEntity<List<CitiesPerStateDTO>> getCitiesPerState() {
        List<CitiesPerStateDTO> numberOfCitiesPerState = cityService.getNumberOfCitiesPerState();
        return ResponseEntity.ok(numberOfCitiesPerState);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> searchWithFilter(@RequestParam(name = "column") String column,
                                              @RequestParam(name = "keyword") String keyword) {
        List<City> cities = cityService.filterCityByColumnAndKeyWord(column, keyword);
        if (cities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/registers-per-column")
    public ResponseEntity<?> getRecordsByColumns(@RequestParam(name = "column") String column){
        Long recordsByColumn = cityService.getRecordsByColumn(column);
        return ResponseEntity.ok(recordsByColumn);
    }

    @GetMapping("/total")
    public ResponseEntity<?> registerCount() {
        RegisterCountDTO registerCount = cityService.getRegisterCount();
        return ResponseEntity.ok(registerCount);
    }

    @GetMapping("/most-distanced")
    public ResponseEntity<List<City>> mostDistancedCities(){
        List<City> cities = cityService.mostDistancedCities();
        if (cities.size() > 0){
            return ResponseEntity.ok(cities);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<City> addNewCity(@Valid @RequestBody CityDTO newCity) {
        City city = cityService.addNewCity(newCity);
        return new ResponseEntity<City>(city, HttpStatus.CREATED);
    }

    @DeleteMapping("/{ibgeId}")
    public ResponseEntity<?> deleteCityByIbgeId(@PathVariable Long ibgeId) {
        cityService.deleteCityByIbgeId(ibgeId);
        return ResponseEntity.noContent().build();
    }

}
