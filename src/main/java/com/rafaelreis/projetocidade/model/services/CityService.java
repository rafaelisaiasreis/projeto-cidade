package com.rafaelreis.projetocidade.model.services;

import com.rafaelreis.projetocidade.model.DTO.CitiesPerStateDTO;
import com.rafaelreis.projetocidade.model.DTO.CityDTO;
import com.rafaelreis.projetocidade.model.DTO.StatesWithMostAndLessCitiesDTO;
import com.rafaelreis.projetocidade.model.entities.City;
import com.rafaelreis.projetocidade.model.projection.CitiesPerStateProjection;
import com.rafaelreis.projetocidade.model.projection.RecordsProjection;
import com.rafaelreis.projetocidade.model.repositories.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Optional<City> findCityByIbgeId(Long ibgeId) {
        return cityRepository.findByIbgeId(ibgeId);
    }

    public City addNewCity(CityDTO newCity) {
        City city = modelMapper.map(newCity, City.class);
        return cityRepository.saveAndFlush(city);
    }

    public void deleteCityByIbgeId(Long ibgeId) {
        Optional<City> cityByIbgeId = findCityByIbgeId(ibgeId);
        cityByIbgeId.ifPresent(city -> cityRepository.delete(city));
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
                .stateMostCities(CitiesPerStateDTO.parseToCitiesPerStateDTO(stateMostCities))
                .build();
    }

    public List<CitiesPerStateDTO> getNumberOfCitiesPerState() {
        List<CitiesPerStateProjection> numberOfCitiesPerState =
                cityRepository.findNumberOfCitiesPerState();
        return numberOfCitiesPerState
                .stream()
                .map(CitiesPerStateDTO::parseToCitiesPerStateDTO)
                .collect(Collectors.toList());
    }

    public List<City> filterCityByColumnAndKeyWord(String column, String keyWord) {
        return cityRepository.findCitiesFilterByColumnAndKeyWord(column, keyWord);
    }

    public Long getRecordsByColumn(String column) {
        Optional<RecordsProjection> totalRegistersByColumn =
                cityRepository.findTotalRegistersByColumn(column);
        if (totalRegistersByColumn.isEmpty()) {
            return 0L;
        }
        return totalRegistersByColumn.get().getRecords();
    }

}
