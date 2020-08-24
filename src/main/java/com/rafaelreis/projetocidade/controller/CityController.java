package com.rafaelreis.projetocidade.controller;

import com.rafaelreis.projetocidade.model.DTO.CitiesPerStateDTO;
import com.rafaelreis.projetocidade.model.DTO.CityDTO;
import com.rafaelreis.projetocidade.model.DTO.RegisterCountDTO;
import com.rafaelreis.projetocidade.model.DTO.StatesWithMostAndLessCitiesDTO;
import com.rafaelreis.projetocidade.model.entities.City;
import com.rafaelreis.projetocidade.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cities")
@Api(value = "API REST Cities")
public class CityController {

  @Autowired
  private CityService cityService;

  @ApiOperation(value = "Get a city from Ibge ID")
  @GetMapping("/{ibgeId}")
  public ResponseEntity<City> getCityByIbgeId(@PathVariable Long ibgeId) {
    City cityByIbgeId = cityService.findCityByIbgeId(ibgeId);
    return ResponseEntity.ok(cityByIbgeId);
  }

  @ApiOperation(value = "Get a list of capital order by name")
  @GetMapping("/capitals")
  public ResponseEntity<List<City>> getAllCapitalsOrderByName(
      @RequestParam(name = "capital") Boolean capital) {
    List<City> allCapitalsOrderByName = cityService.findAllCapitalsOrderByName(capital);
    return ResponseEntity.ok(allCapitalsOrderByName);
  }

  @ApiOperation(value = "Get a list of cities group by UF")
  @GetMapping("/states")
  public ResponseEntity<List<City>> getCitiesByUF(@RequestParam(name = "uf") String uf) {
    List<City> allCapitalsOrderByName = cityService.findAllCitiesByUF(uf);
    return ResponseEntity.ok(allCapitalsOrderByName);
  }

  @ApiOperation(value = "Get states with most an less cities")
  @GetMapping("/states/most-less-cities")
  public ResponseEntity<StatesWithMostAndLessCitiesDTO> getStatesWithMoreAndLessCities() {
    StatesWithMostAndLessCitiesDTO statesWithMostAndLessCities =
        cityService.getStatesWithMostAndLessCities();
    return ResponseEntity.ok(statesWithMostAndLessCities);
  }

  @ApiOperation(value = "Get a list of cities filter by UF")
  @GetMapping("/per-state")
  public ResponseEntity<List<CitiesPerStateDTO>> getCitiesPerState() {
    List<CitiesPerStateDTO> numberOfCitiesPerState = cityService.getNumberOfCitiesPerState();
    return ResponseEntity.ok(numberOfCitiesPerState);
  }

  @ApiOperation(value = "Get a list of cities filter by field name and a keyword")
  @GetMapping("/filter")
  public ResponseEntity<?> searchWithFilter(@RequestParam(name = "field") String field,
      @RequestParam(name = "keyword") String keyword) {
    List<City> cities = cityService.filterCityByFieldAndKeyWord(field, keyword);
    return ResponseEntity.ok(cities);
  }

  @ApiOperation(value = "Get a total of registers filter by field name")
  @GetMapping("/registers-per-column")
  public ResponseEntity<RegisterCountDTO> getRecordsByColumns(@RequestParam(name = "field") String field) {
    RegisterCountDTO recordsByColumn = cityService.getRecordsByField(field);
    return ResponseEntity.ok(recordsByColumn);
  }

  @ApiOperation(value = "Get the count of all Registers")
  @GetMapping("/total")
  public ResponseEntity<?> registerCount() {
    RegisterCountDTO registerCount = cityService.getRegisterCount();
    return ResponseEntity.ok(registerCount);
  }

  @ApiOperation(value = "Get the cities most distanced")
  @GetMapping("/most-distanced")
  public ResponseEntity<List<City>> mostDistancedCities() {
    List<City> cities = cityService.mostDistancedCities();
    return ResponseEntity.ok(cities);
  }

  @ApiOperation(value = "Add a new city")
  @PostMapping
  public ResponseEntity<City> addNewCity(@Valid @RequestBody CityDTO newCity) {
    City city = cityService.addNewCity(newCity);
    return new ResponseEntity<City>(city, HttpStatus.CREATED);
  }

  @ApiOperation(value = "Delete a city")
  @DeleteMapping("/{ibgeId}")
  public ResponseEntity<?> deleteCityByIbgeId(@PathVariable Long ibgeId) {
    cityService.deleteCityByIbgeId(ibgeId);
    return ResponseEntity.noContent().build();
  }

}
