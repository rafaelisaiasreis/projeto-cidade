package com.rafaelreis.projetocidade.model.services;

import com.rafaelreis.projetocidade.exceptions.CityNotFoundException;
import com.rafaelreis.projetocidade.model.entities.City;
import com.rafaelreis.projetocidade.model.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public City findCityById(Long id){
        return cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(id));
    }

    public City addNewCity(City newCity){
        return cityRepository.saveAndFlush(newCity);
    }

    public void deleteCityById(Long id){
        findCityById(id);
        cityRepository.deleteById(id);
    }

}
