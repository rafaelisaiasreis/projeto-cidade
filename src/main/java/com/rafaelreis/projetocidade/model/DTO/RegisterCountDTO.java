package com.rafaelreis.projetocidade.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterCountDTO {
    private Long totalRegisters;
}
