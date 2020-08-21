package com.rafaelreis.projetocidade.config;

import com.rafaelreis.projetocidade.services.LoadTableCityFromCSVFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
@Slf4j
@ConditionalOnExpression("${projeto.cidade.load-city-table}")
public class LoadTableCity {

    @Bean
    public CommandLineRunner loadTable(LoadTableCityFromCSVFile loadTableCityFromCSVFile){
        return args -> {
            LocalDateTime start = LocalDateTime.now();
            log.info("Start load table city...Time: {}", start);

            loadTableCityFromCSVFile.load();

            LocalDateTime finish = LocalDateTime.now();
            log.info("Finish load table city...Time: {} - Duration: {}", finish,
                    Duration.between(start,finish));
        };
    }
}
