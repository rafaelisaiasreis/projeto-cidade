package com.rafaelreis.projetocidade.model.entities;

import com.rafaelreis.projetocidade.model.DTO.CityCsvFileDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "city")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class City {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long ibgeId;

  private String uf;

  private String name;

  private Boolean capital;

  @Column(name = "lon")
  private Double longitude;

  @Column(name = "lat")
  private Double latitude;

  @Column(name = "no_accents")
  private String noAccents;

  @Column(name = "alternative_names")
  private String alternativeNames;

  @Column(name = "microregion")
  private String microRegion;

  @Column(name = "mesoregion")
  private String mesoregion;

  public static City parseDtoToCityObject(CityCsvFileDTO cityDTO) {
    City newCity = City.builder().ibgeId(cityDTO.getIbgeId()).uf(cityDTO.getUf())
        .name(cityDTO.getName()).capital(cityDTO.getCapital().equalsIgnoreCase("true"))
        .longitude(cityDTO.getLongitude()).latitude(cityDTO.getLatitude())
        .noAccents(cityDTO.getNoAccents()).alternativeNames(cityDTO.getAlternativeNames())
        .microRegion(cityDTO.getMicroRegion()).mesoregion(cityDTO.getMesoregion()).build();
    return newCity;
  }

}
