package com.rafaelreis.projetocidade.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorFields {
    private String field;
    private String message;
}
