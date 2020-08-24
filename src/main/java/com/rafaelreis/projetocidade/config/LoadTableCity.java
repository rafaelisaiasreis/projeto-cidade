package com.rafaelreis.projetocidade.config;

import com.rafaelreis.projetocidade.model.entities.Control;
import com.rafaelreis.projetocidade.model.repository.ControlRepository;
import com.rafaelreis.projetocidade.service.LoadTableCityFromCSVFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@Slf4j
@ConditionalOnExpression("${projeto.cidade.load-city-table}")
public class LoadTableCity {

  @Autowired
  ControlRepository controlRepository;

  @Bean
  public CommandLineRunner loadTable(LoadTableCityFromCSVFile loadTableCityFromCSVFile) {
    return args -> {
      List<Control> control = controlRepository.findAll();

      if (control.isEmpty()) {

        try {
          LocalDateTime start = LocalDateTime.now();
          log.info("Start load table city...Time: {}", start);

          loadTableCityFromCSVFile.load();
          Control buildControl =
              Control.builder().data_importacao(LocalDateTime.now()).situacao(true).build();
          controlRepository.saveAndFlush(buildControl);

          LocalDateTime finish = LocalDateTime.now();
          log.info("Finish load table city...Time: {} - Duration: {}", finish,
              Duration.between(start, finish));

        } catch (Exception ex) {
          log.error("Could not load table city from CSV file");
          return;
        }
      }


    };
  }
}
