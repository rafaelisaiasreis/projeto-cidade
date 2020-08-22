package com.rafaelreis.projetocidade.model.projection;

import com.rafaelreis.projetocidade.model.repository.CityRepository;

/**
 * Projection that returns information about cities per state
 *
 * @author Rafael Isaias Reis <rafaelisaiasreis@gmail.com />
 * @version 0.1
 * @since 0.1
 * @see CityRepository
 */
public interface CitiesPerStateProjection {

    String getUf();

    Integer getCities();

}
