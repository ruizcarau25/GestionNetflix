package com.example.GestionNetflix.GestionNetflix.error.notfound;

public class DirectorNoEncontradoException extends RuntimeException {

    public DirectorNoEncontradoException() {
        super("No hay directores registrados");
    }

    public DirectorNoEncontradoException(Long id) {
        super("Director no encontrado con id " + id);
    }

}
