package com.rafaelreis.projetocidade.model.repository;

import com.rafaelreis.projetocidade.model.entities.City;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CityCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<City> filterCityByFieldAndKeyWord(String column, String keyword) {
        String sql = "SELECT C FROM city as C WHERE C." + column + " LIKE '%" + keyword + "%'";
        return entityManager.createQuery(sql, City.class).getResultList();
    }

    public Long filterDistinctRegisterFromField(String column) {
        String sql = "SELECT COUNT(DISTINCT C." + column + ") FROM city as C";
        return (Long) entityManager.createQuery(sql).getSingleResult();
    }
}
