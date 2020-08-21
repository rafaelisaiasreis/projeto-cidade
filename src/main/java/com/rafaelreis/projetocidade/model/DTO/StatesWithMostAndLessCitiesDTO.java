package com.rafaelreis.projetocidade.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatesWithMostAndLessCitiesDTO {
    private CitiesPerStateDTO stateMostCities;
    private CitiesPerStateDTO stateLessCities;
}
