package com.rafaelreis.projetocidade.model.repository;

import com.rafaelreis.projetocidade.model.entities.Control;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlRepository extends JpaRepository<Control, Integer> {

}
