package com.rafaelreis.projetocidade.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ErrorMessage {
    private Integer status;
    private LocalDateTime dateTime;
    private String title;
    private List<ErrorFields> fields;
}
