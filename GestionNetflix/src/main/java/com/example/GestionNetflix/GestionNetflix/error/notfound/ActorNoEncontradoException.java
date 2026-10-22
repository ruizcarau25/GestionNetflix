package com.example.GestionNetflix.GestionNetflix.error.notfound;

public class ActorNoEncontradoException extends RuntimeException {

    public ActorNoEncontradoException() {
        super("No hay actores registrados");
    }

    public ActorNoEncontradoException(Long id) {
        super("Actor no encontrado con id " + id);
    }


}
