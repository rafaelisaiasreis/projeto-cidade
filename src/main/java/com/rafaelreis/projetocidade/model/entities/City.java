package com.rafaelreis.projetocidade.model.entities;

import com.rafaelreis.projetocidade.model.DTO.CityDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "city")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class City {

  @Id
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

  public static City parseDtoToCidadeObject(CityDTO cidadeDTO){
    City novaCidade = City.builder()
            .ibgeId(cidadeDTO.getIbgeId())
            .uf(cidadeDTO.getUf())
            .name(cidadeDTO.getName())
            .capital(cidadeDTO.getCapital().equalsIgnoreCase("true"))
            .longitude(cidadeDTO.getLongitude())
            .latitude(cidadeDTO.getLatitude())
            .noAccents(cidadeDTO.getNoAccents())
            .alternativeNames(cidadeDTO.getAlternativeNames())
            .microRegion(cidadeDTO.getMicroRegion())
            .mesoregion(cidadeDTO.getMesoregion())
            .build();
    return novaCidade;
  }

}
