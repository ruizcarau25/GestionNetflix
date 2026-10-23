package com.example.GestionNetflix.GestionNetflix.error;

public class PeliculaYaExisteException extends RuntimeException {

    public PeliculaYaExisteException(String titulo) {
        super("Ya existe una película con el título '" + titulo + "'");
    }

}
