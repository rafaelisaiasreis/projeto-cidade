package com.rafaelreis.projetocidade.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {

    private Long ibgeId;
    private String uf;
    private String name;
    private Boolean capital;
    private Double longitude;
    private Double latitude;
    private String noAccents;
    private String alternativeNames;
    private String microRegion;
    private String mesoregion;

}
