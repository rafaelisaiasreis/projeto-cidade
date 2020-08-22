package com.rafaelreis.projetocidade.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.rafaelreis.projetocidade.exception.CityNotFountException;
import com.rafaelreis.projetocidade.exception.DomainException;
import com.rafaelreis.projetocidade.model.DTO.CityDTO;
import com.rafaelreis.projetocidade.model.entities.City;
import com.rafaelreis.projetocidade.model.repository.CityCustomRepository;
import com.rafaelreis.projetocidade.model.repository.CityRepository;

@RunWith(SpringRunner.class)
public class CityServiceTest {

  @Spy
  CityService cityService;
  @Mock
  private CityRepository cityRepository;
  @Mock
  private CityCustomRepository cityCustomRepository;
  @Mock
  private ModelMapper modelMapper;

  @Before
  public void setUp() throws Exception {
    this.cityService =
        Mockito.spy(new CityService(cityRepository, cityCustomRepository, modelMapper));
  }

  @Test
  public void findCityByIbgeId_TudoValido_OK() {
    when(cityRepository.findByIbgeId(anyLong()))
        .thenReturn(Optional.of(City.builder().ibgeId(4214300L).name("Rancho Queimado").build()));
    City cityByIbgeId = cityService.findCityByIbgeId(4214300L);
    assertEquals(4214300L, cityByIbgeId.getIbgeId(),
        "Dever retornar uma cidade na busca por ibgeid");
  }

  @Test
  public void findCityByIbgeId_NaoEncontrouCidade_OK() {
    doThrow(new CityNotFountException("City not found")).when(cityRepository)
        .findByIbgeId(anyLong());
    try {
      cityService.findCityByIbgeId(4214300L);
    } catch (CityNotFountException ex) {
      assertEquals("City not found", ex.getMessage(), "Exception CityNotFound trhows");
    }
  }

  @Test
  public void addNewCity_TudoValido_OK() {
    CityDTO cityDTO = CityDTO.builder().ibgeId(4214300L).name("Rancho Queimado").build();
    City city = City.builder().ibgeId(4214300L).name("Rancho Queimado").build();

    when(cityRepository.findByIbgeId(anyLong())).thenReturn(Optional.empty());
    when(modelMapper.map(any(CityDTO.class), any())).thenReturn(city);
    when(cityRepository.saveAndFlush(any(City.class))).thenReturn(city);

    City newCity = cityService.addNewCity(cityDTO);
    assertEquals(newCity.getIbgeId(), cityDTO.getIbgeId(), "Add new city");
  }

  @Test
  public void addNewCity_AlreadyExists_OK() {
    CityDTO cityDTO = CityDTO.builder().ibgeId(4214300L).name("Rancho Queimado").build();
    City city = City.builder().ibgeId(4214300L).name("Rancho Queimado").build();

    when(cityRepository.findByIbgeId(anyLong())).thenReturn(Optional.of(city));

    try {
      cityService.addNewCity(cityDTO);
    } catch (DomainException ex) {
      assertEquals("This IbgeId already exists!", ex.getMessage(), "DomainException trhows");
    }
  }

  @Test
  public void filterCityByFieldAndKeyWord_OK() {
    List<City> cities =
        Arrays.asList(City.builder().ibgeId(4214300L).name("Rancho Queimado").build(),
            City.builder().ibgeId(4214508L).name("Rio do Campo").build());
    when(cityCustomRepository.filterCityByFieldAndKeyWord(anyString(), anyString()))
        .thenReturn(cities);
    List<City> citiesFilter = cityService.filterCityByFieldAndKeyWord("name", "polis");
    assertEquals(cities.get(1).getIbgeId(), citiesFilter.get(1).getIbgeId(),
        "Retunsa list of cities filter by field and keyword");
  }

  @Test
  public void filterCityByFieldAndKeyWord_NotFound_OK() {
    List<City> cities = new ArrayList<>();
    when(cityCustomRepository.filterCityByFieldAndKeyWord(anyString(), anyString()))
        .thenReturn(cities);
    try {
      List<City> citiesFilter = cityService.filterCityByFieldAndKeyWord("name", "polis");
    } catch (CityNotFountException ex) {
      assertEquals(ex.getMessage(), "Could not find a city from the filters",
          "Throws CityNotFoundException");
    }
  }

  @Test
  public void mostDistancedCities_TudoValido_OK() {
    List<City> cities = Arrays.asList(
        City.builder().ibgeId(4214300L).name("Rancho Queimado").latitude(-27.6772088641)
            .longitude(-49.00843238).build(),
        City.builder().ibgeId(4214508L).name("Rio do Campo").latitude(-26.9423084311)
            .longitude(-50.139588913).build(),
        City.builder().ibgeId(4214805L).name("Rio do Sul").latitude(-27.2179032254)
            .longitude(-49.6432375291).build());
    when(cityRepository.findAll()).thenReturn(cities);
    List<City> citiesMostDistanced = cityService.mostDistancedCities();
    assertEquals(citiesMostDistanced.size(), 2, "Find cities most distanced");
    assertEquals(citiesMostDistanced.get(0).getName(), "Rancho Queimado",
        "Find cities most distanced");
    assertEquals(citiesMostDistanced.get(1).getName(), "Rio do Campo",
        "Find cities most distanced");

  }

  @Test
  public void mostDistancedCities_NotFound_OK() {
    List<City> cities = new ArrayList<>();
    when(cityRepository.findAll()).thenReturn(cities);
    try {
      cityService.mostDistancedCities();
    } catch (CityNotFountException ex) {
      assertEquals(ex.getMessage(), "Could not find the most distanced cities");
    }
  }

}
