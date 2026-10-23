package com.example.GestionNetflix.GestionNetflix.error.notfound;

public class PeliculaNoEncontradaException extends EntidadNoEncontradaException {
    public PeliculaNoEncontradaException() {
        super("No hay películas registradas");
    }

    public PeliculaNoEncontradaException(Long id) {
        super("Película no encontrada con id " + id);
    }
}
