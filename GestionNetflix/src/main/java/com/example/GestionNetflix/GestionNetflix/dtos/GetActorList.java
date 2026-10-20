package com.example.GestionNetflix.GestionNetflix.dtos;

import model.Actor;
import model.Pelicula;

import java.util.List;

public record GetActorList(String nombre, List<Pelicula> peliculas) {

    public static GetActorList of(Actor a) {
        return new GetActorList(
                a.getNombre(),
                a.getPeliculas()
        );
    }

}
