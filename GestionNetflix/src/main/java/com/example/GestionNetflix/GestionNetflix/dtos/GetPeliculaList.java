package com.example.GestionNetflix.GestionNetflix.dtos;

import com.example.GestionNetflix.GestionNetflix.model.Actor;
import com.example.GestionNetflix.GestionNetflix.model.Director;
import com.example.GestionNetflix.GestionNetflix.model.Pelicula;

import java.time.LocalDate;
import java.util.List;

public record GetPeliculaList(String titulo, String genero,
                              LocalDate fechaEstreno, Director director,
                              List<Actor> actores) {

    public static GetPeliculaList of(Pelicula p) {
        return  new GetPeliculaList(
                p.getTitulo(),
                p.getGenero(),
                p.getFechaEstreno(),
                p.getDirector(),
                p.getActores()
        );
    }
}
