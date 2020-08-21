package com.rafaelreis.projetocidade.repositories;

import com.rafaelreis.projetocidade.model.entities.City;
import com.rafaelreis.projetocidade.model.projection.CitiesPerStateProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

    List<City> findByCapitalOrderByNameAsc(Boolean capital);

    List<City> findByUf(String uf);

    Optional<City> findByIbgeId(Long ibgeId);

    @Query(value = "select uf, count(*) as cities from city " +
            "group by uf order by 2 desc limit 1;", nativeQuery = true)
    CitiesPerStateProjection findStateMostCities();

    @Query(value = "select uf, count(*) as cities from city " +
            "group by uf order by 2 asc limit 1;", nativeQuery = true)
    CitiesPerStateProjection findStateLessCities();

    @Query(value = "select uf, count(*) as cities from city " +
            "group by uf ;", nativeQuery = true)
    List<CitiesPerStateProjection> findNumberOfCitiesPerState();

}
