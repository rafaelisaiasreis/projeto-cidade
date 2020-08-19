package com.rafaelreis.projetocidade.model.services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.rafaelreis.projetocidade.model.DTO.CityDTO;
import com.rafaelreis.projetocidade.model.entities.City;
import com.rafaelreis.projetocidade.model.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class LoadTableCityFromCSVFile {

    @Value("${projeto.cidade.csv.path}")
    private String pathToCitiesCsvFile;

    @Autowired
    private CityRepository cidadeRepository;

    public void load() {
        List<CityDTO> citiesDTOGroup = csvToBean();
        try {
            citiesDTOGroup.parallelStream().forEach(
                    city -> cidadeRepository.saveAndFlush(City.parseDtoToCityObject(city)));
        } catch (Exception ex) {
            throw new RuntimeException("Load city table failed!");
        }
    }

    private List<CityDTO> csvToBean() {
        try (Reader reader = Files.newBufferedReader(
                Paths.get(pathToCitiesCsvFile))) {
            CsvToBean<CityDTO> csvToBean = new CsvToBeanBuilder<CityDTO>(reader)
                    .withType(CityDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (Exception ex) {
            throw new RuntimeException("Error to create bean from csv file");
        }
    }

}
