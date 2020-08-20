package com.rafaelreis.projetocidade.model.repositories;

import com.rafaelreis.projetocidade.model.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByCapitalOrderByNameAsc(Boolean capital);

    List<City> findByUf(String uf);

    Optional<City> findByIbgeId(Long ibgeId);

}
