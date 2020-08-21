package com.rafaelreis.projetocidade.model.repositories;

import com.rafaelreis.projetocidade.model.entities.City;
import com.rafaelreis.projetocidade.model.projection.CitiesPerStateProjection;
import com.rafaelreis.projetocidade.model.projection.RecordsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
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

    @Query(value = "select * from city where :column like '%:keyword%';", nativeQuery = true)
    List<City> findCitiesFilterByColumnAndKeyWord(@Param("column") String column,
                                                  @Param("keyword") String keyword);

    @Query(value = "select count(distinct :column) as records from city;", nativeQuery = true)
    Optional<RecordsProjection> findTotalRegistersByColumn(@Param("column") String column);



}
