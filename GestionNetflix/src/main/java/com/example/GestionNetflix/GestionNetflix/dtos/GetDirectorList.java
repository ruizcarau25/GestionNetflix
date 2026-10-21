package com.example.GestionNetflix.GestionNetflix.dtos;

import com.example.GestionNetflix.GestionNetflix.model.Director;
import com.example.GestionNetflix.GestionNetflix.model.Pelicula;

import java.util.List;

public record GetDirectorList(String nombre, Integer anioNacimiento,
                              List<Pelicula> peliculas) {
    public static GetDirectorList of(Director d) {
        return new GetDirectorList(
                d.getNombre(),
                d.getAnioNacimiento(),
                d.getPeliculas()
        );
    }

}
