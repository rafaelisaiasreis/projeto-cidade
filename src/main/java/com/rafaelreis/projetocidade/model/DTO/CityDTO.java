package com.rafaelreis.projetocidade.model.DTO;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {

  @CsvBindByName(column = "ibge_id")
  private Long ibgeId;

  @CsvBindByName
  private String uf;

  @CsvBindByName
  private String name;

  @CsvBindByName
  private String capital;

  @CsvBindByName(column = "lon")
  private Double longitude;

  @CsvBindByName(column = "lat")
  private Double latitude;

  @CsvBindByName(column = "no_accents")
  private String noAccents;

  @CsvBindByName(column = "alternative_names")
  private String alternativeNames;

  @CsvBindByName(column = "microregion")
  private String microRegion;

  @CsvBindByName(column = "mesoregion")
  private String mesoregion;

}
