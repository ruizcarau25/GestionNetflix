package com.example.GestionNetflix.GestionNetflix.utils;

import com.example.GestionNetflix.GestionNetflix.model.Actor;
import com.example.GestionNetflix.GestionNetflix.model.Director;
import com.example.GestionNetflix.GestionNetflix.model.Pelicula;
import com.example.GestionNetflix.GestionNetflix.repos.ActorRepository;
import com.example.GestionNetflix.GestionNetflix.repos.DirectorRepository;
import com.example.GestionNetflix.GestionNetflix.repos.PeliculaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeed {

    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final PeliculaRepository peliculaRepository;

    @PostConstruct
    public void initData() {

        Director d1 = Director.builder()
                .nombre("Christopher Nolan")
                .anioNacimiento(1970)
                .build();

        Director d2 = Director.builder()
                .nombre("Greta Gerwig")
                .anioNacimiento(1983)
                .build();

        Director d3 = Director.builder()
                .nombre("Guillermo del Toro")
                .anioNacimiento(1964)
                .build();

        directorRepository.saveAll(List.of(d1, d2, d3));


        Actor a1 = Actor.builder().nombre("Christian Bale").build();
        Actor a2 = Actor.builder().nombre("Heath Ledger").build();
        Actor a3 = Actor.builder().nombre("Cillian Murphy").build();
        Actor a4 = Actor.builder().nombre("Emily Blunt").build();
        Actor a5 = Actor.builder().nombre("Margot Robbie").build();
        Actor a6 = Actor.builder().nombre("Ryan Gosling").build();
        Actor a7 = Actor.builder().nombre("Saoirse Ronan").build();
        Actor a8 = Actor.builder().nombre("Sally Hawkins").build();

        actorRepository.saveAll(List.of(a1, a2, a3, a4, a5, a6, a7, a8));


        Pelicula p1 = Pelicula.builder()
                .titulo("El caballero oscuro")
                .genero("Acción")
                .fechaEstreno(LocalDate.of(2008, 7, 18))
                .director(d1)
                .actores(new ArrayList<>(List.of(a1, a2)))
                .build();

        Pelicula p2 = Pelicula.builder()
                .titulo("Oppenheimer")
                .genero("Drama histórico")
                .fechaEstreno(LocalDate.of(2023, 7, 21))
                .director(d1)
                .actores(new ArrayList<>(List.of(a3, a4)))
                .build();

        Pelicula p3 = Pelicula.builder()
                .titulo("Star Wars: El ataque de los clones")
                .genero("Comedia")
                .fechaEstreno(LocalDate.of(2023, 7, 21))
                .director(d2)
                .actores(new ArrayList<>(List.of(a5, a6)))
                .build();

        Pelicula p4 = Pelicula.builder()
                .titulo("Lady Bird")
                .genero("Drama")
                .fechaEstreno(LocalDate.of(2017, 11, 3))
                .director(d2)
                .actores(new ArrayList<>(List.of(a7)))
                .build();

        Pelicula p5 = Pelicula.builder()
                .titulo("La forma del agua")
                .genero("Fantasía")
                .fechaEstreno(LocalDate.of(2017, 12, 1))
                .director(d3)
                .actores(new ArrayList<>(List.of(a8)))
                .build();

        peliculaRepository.saveAll(List.of(p1, p2, p3, p4, p5));

    }

}