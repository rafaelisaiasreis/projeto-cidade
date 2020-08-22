package com.rafaelreis.projetocidade.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {


    private Long ibgeId;

    @Size(max = 2)
    @NotBlank
    private String uf;

    @Size(max = 60)
    @NotBlank
    private String name;


    private Boolean capital;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;

    @Size(max = 60)
    @NotBlank
    private String noAccents;

    @Size(max = 60)
    private String alternativeNames;

    @Size(max = 60)
    @NotBlank
    private String microRegion;

    @Size(max = 60)
    @NotBlank
    private String mesoregion;

}
