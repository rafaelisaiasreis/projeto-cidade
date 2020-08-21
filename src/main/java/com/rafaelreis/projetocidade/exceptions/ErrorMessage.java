package com.rafaelreis.projetocidade.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorMessage {
    private Integer status;
    private LocalDateTime dateTime;
    private String title;
    private List<ErrorFields> fields;
}
