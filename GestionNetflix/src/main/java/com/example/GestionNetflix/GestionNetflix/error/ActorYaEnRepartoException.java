package com.example.GestionNetflix.GestionNetflix.error;

public class ActorYaEnRepartoException extends RuntimeException {

    public ActorYaEnRepartoException(Long peliculaId, Long actorId) {
        super("El actor con id " + actorId
                + " ya forma parte del reparto de la película con id " + peliculaId);
    }

}
