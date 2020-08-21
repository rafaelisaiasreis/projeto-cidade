package com.rafaelreis.projetocidade.model.projection;

import com.rafaelreis.projetocidade.model.repositories.CityRepository;

/**
 * Projection that returns information about column records
 *
 * @author Rafael Isaias Reis <rafaelisaiasreis@gmail.com />
 * @version 0.1
 * @since 0.1
 * @see CityRepository
 */
public interface RecordsProjection {

    Long getRecords();

}
