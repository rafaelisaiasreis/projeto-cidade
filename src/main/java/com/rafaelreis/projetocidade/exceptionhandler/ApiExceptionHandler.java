package com.rafaelreis.projetocidade.exceptionhandler;

import com.rafaelreis.projetocidade.exception.CityNotFountException;
import com.rafaelreis.projetocidade.exception.DomainException;
import com.rafaelreis.projetocidade.exception.ErrorFields;
import com.rafaelreis.projetocidade.exception.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DomainException.class)
  public ResponseEntity<Object> handleDomainException(DomainException ex, WebRequest request) {
    ErrorMessage buildResponse = ErrorMessage.builder().status(HttpStatus.BAD_REQUEST.value())
        .title(ex.getMessage()).dateTime(LocalDateTime.now()).build();
    return handleExceptionInternal(ex, buildResponse, new HttpHeaders(), HttpStatus.BAD_GATEWAY,
        request);
  }

  @ExceptionHandler(CityNotFountException.class)
  public ResponseEntity<Object> handleCityNotFoundException(DomainException ex,
      WebRequest request) {
    ErrorMessage buildResponse = ErrorMessage.builder().status(HttpStatus.NOT_FOUND.value())
        .title(ex.getMessage()).dateTime(LocalDateTime.now()).build();
    return handleExceptionInternal(ex, buildResponse, new HttpHeaders(), HttpStatus.BAD_GATEWAY,
        request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    List<ErrorFields> fieldsGroup = new ArrayList<>();

    ex.getBindingResult().getAllErrors().forEach(objectError -> {
      fieldsGroup.add(ErrorFields.builder().field(((FieldError) objectError).getField())
          .message(objectError.getDefaultMessage()).build());
    });

    ErrorMessage invalid_fields = ErrorMessage.builder().status(status.value())
        .title("Invalid fields").dateTime(LocalDateTime.now()).fields(fieldsGroup).build();
    return super.handleExceptionInternal(ex, invalid_fields, headers, status, request);
  }
}
