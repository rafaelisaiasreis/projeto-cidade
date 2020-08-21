package com.rafaelreis.projetocidade.model.DTO;

import com.rafaelreis.projetocidade.model.projection.CitiesPerStateProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitiesPerStateDTO {
    private String uf;
    private Integer numberOfCities;

    public static CitiesPerStateDTO parseToCitiesPerStateDTO(CitiesPerStateProjection citiesPerState){
        return CitiesPerStateDTO.builder()
                .uf(citiesPerState.getUf())
                .numberOfCities(citiesPerState.getCities())
                .build();
    }
}
