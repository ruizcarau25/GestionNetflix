package com.example.GestionNetflix.GestionNetflix.dtos;

import com.example.GestionNetflix.GestionNetflix.model.Actor;
import com.example.GestionNetflix.GestionNetflix.model.Director;
import com.example.GestionNetflix.GestionNetflix.model.Pelicula;

import java.time.LocalDate;
import java.util.List;

public record GetPeliculaList(Long id, String titulo, String genero,
                              LocalDate fechaEstreno,
                              DirectorSimpleDto director,
                              List<ActorSimpleDto> actores) {

    public static GetPeliculaList of(Pelicula p) {
        return new GetPeliculaList(
                p.getId(),
                p.getTitulo(),
                p.getGenero(),
                p.getFechaEstreno(),
                DirectorSimpleDto.of(p.getDirector()),
                p.getActores().stream().map(ActorSimpleDto::of).toList()
        );
    }
}
