package com.example.GestionNetflix.GestionNetflix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Actor {

    @Id @GeneratedValue
    private Long id;
    private String nombre;

    @ManyToMany(mappedBy = "actores")
    @Builder.Default
    private List<Pelicula> peliculas = new ArrayList<>();

}
