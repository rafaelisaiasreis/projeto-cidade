package com.rafaelreis.projetocidade.service;

import com.rafaelreis.projetocidade.exception.CityNotFountException;
import com.rafaelreis.projetocidade.exception.DomainException;
import com.rafaelreis.projetocidade.model.DTO.CitiesPerStateDTO;
import com.rafaelreis.projetocidade.model.DTO.CityDTO;
import com.rafaelreis.projetocidade.model.DTO.RegisterCountDTO;
import com.rafaelreis.projetocidade.model.DTO.StatesWithMostAndLessCitiesDTO;
import com.rafaelreis.projetocidade.model.entities.City;
import com.rafaelreis.projetocidade.model.projection.CitiesPerStateProjection;
import com.rafaelreis.projetocidade.model.repository.CityCustomRepository;
import com.rafaelreis.projetocidade.model.repository.CityRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CityService {

  @Autowired
  private CityRepository cityRepository;
  @Autowired
  private CityCustomRepository cityCustomRepository;
  @Autowired
  private ModelMapper modelMapper;

  public City findCityByIbgeId(Long ibgeId) {
    return cityRepository.findByIbgeId(ibgeId)
        .orElseThrow(() -> new CityNotFountException("City not found"));
  }

  public City addNewCity(CityDTO newCity) {
    Optional<City> byIbgeId = cityRepository.findByIbgeId(newCity.getIbgeId());
    if (byIbgeId.isPresent()) {
      throw new DomainException("This IbgeId already exists!");
    }
    City city = modelMapper.map(newCity, City.class);
    return cityRepository.saveAndFlush(city);
  }

  public void deleteCityByIbgeId(Long ibgeId) {
    City cityByIbgeId = findCityByIbgeId(ibgeId);
    cityRepository.delete(cityByIbgeId);
  }

  public List<City> findAllCapitalsOrderByName(Boolean capital) {
    return cityRepository.findByCapitalOrderByNameAsc(capital);
  }

  public List<City> findAllCitiesByUF(String uf) {
    return cityRepository.findByUf(uf);
  }

  public StatesWithMostAndLessCitiesDTO getStatesWithMostAndLessCities() {
    CitiesPerStateProjection stateMostCities = cityRepository.findStateMostCities();
    CitiesPerStateProjection stateLessCities = cityRepository.findStateLessCities();

    return StatesWithMostAndLessCitiesDTO.builder()
        .stateLessCities(CitiesPerStateDTO.parseToCitiesPerStateDTO(stateLessCities))
        .stateMostCities(CitiesPerStateDTO.parseToCitiesPerStateDTO(stateMostCities)).build();
  }

  public List<CitiesPerStateDTO> getNumberOfCitiesPerState() {
    List<CitiesPerStateProjection> numberOfCitiesPerState =
        cityRepository.findNumberOfCitiesPerState();
    return numberOfCitiesPerState.stream().map(CitiesPerStateDTO::parseToCitiesPerStateDTO)
        .collect(Collectors.toList());
  }

  public List<City> filterCityByFieldAndKeyWord(String field, String keyWord) {
    List<City> cities = new ArrayList<>();
    try{
      cities = cityCustomRepository.filterCityByFieldAndKeyWord(field, keyWord);
      if (cities.isEmpty()){ throw new RuntimeException(); }
    } catch (Exception ex){
      throw new CityNotFountException("Could not find a city from the filters");
    }

   return cities;
  }

  public RegisterCountDTO getRecordsByField(String field) {
    RegisterCountDTO registerCountDTO = new RegisterCountDTO();
    try{
      Long count = cityCustomRepository.filterDistinctRegisterFromField(field);
      registerCountDTO.setTotalRegisters(count);
    } catch (Exception ex) {
      throw new CityNotFountException("Could not find a city from the filters");
    }
    return registerCountDTO;
  }

  public RegisterCountDTO getRegisterCount() {
    Long count = cityRepository.count();
    return new RegisterCountDTO(count);
  }

  public List<City> mostDistancedCities() {
    List<City> citiesGroup = cityRepository.findAll();
    if (citiesGroup.size() > 0) {
      List<City> mostDistanced = new ArrayList<>();
      City max = citiesGroup.stream()
          .max(Comparator.comparing(City::getLongitude).thenComparing(City::getLatitude))
          .orElse(null);
      City min = citiesGroup.stream()
          .min(Comparator.comparing(City::getLongitude).thenComparing(City::getLatitude))
          .orElse(null);
      mostDistanced.add(max);
      mostDistanced.add(min);
      return mostDistanced;
    }
    throw new CityNotFountException("Could not find the most distanced cities");
  }

}
