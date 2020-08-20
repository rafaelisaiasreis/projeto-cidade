package com.rafaelreis.projetocidade.model.services;

import com.rafaelreis.projetocidade.exceptions.CityNotFoundException;
import com.rafaelreis.projetocidade.model.DTO.CityDTO;
import com.rafaelreis.projetocidade.model.entities.City;
import com.rafaelreis.projetocidade.model.repositories.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    ModelMapper modelMapper;

    public City findCityByIbgeId(Long ibgeId){
        return cityRepository.findByIbgeId(ibgeId).orElseThrow(() -> new CityNotFoundException(ibgeId));
    }

    public City addNewCity(CityDTO newCity){
        City city = modelMapper.map(newCity, City.class);
        return cityRepository.saveAndFlush(city);
    }

    public void deleteCityByIbgeId(Long ibgeId){
        City cityByIbgeId = findCityByIbgeId(ibgeId);
        cityRepository.delete(cityByIbgeId);
    }

    public List<City> findAllCapitalsOrderByName(Boolean capital){
        return cityRepository.findByCapitalOrderByNameAsc(capital);
    }

    public List<City> findAllCitiesByUF(String uf){
        return cityRepository.findByUf(uf);
    }

}
