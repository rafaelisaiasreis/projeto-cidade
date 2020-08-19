package com.rafaelreis.projetocidade.exceptions;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(Long id) {
        super("Could not find city id " + id);
    }
}
