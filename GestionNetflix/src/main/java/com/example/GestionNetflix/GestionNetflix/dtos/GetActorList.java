package com.example.GestionNetflix.GestionNetflix.dtos;

import com.example.GestionNetflix.GestionNetflix.model.Actor;
import com.example.GestionNetflix.GestionNetflix.model.Pelicula;

import java.util.List;

public record GetActorList(Long id, String nombre) {
    public static GetActorList of(Actor a) {
        return new GetActorList(a.getId(), a.getNombre());
    }
}
