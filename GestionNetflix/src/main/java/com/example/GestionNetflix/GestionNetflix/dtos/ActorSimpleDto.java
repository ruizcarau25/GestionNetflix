package com.example.GestionNetflix.GestionNetflix.dtos;

import com.example.GestionNetflix.GestionNetflix.model.Actor;

public record ActorSimpleDto(Long id, String nombre) {
    public static ActorSimpleDto of(Actor a) {
        return new ActorSimpleDto(a.getId(), a.getNombre());
    }
}
