package com.example.GestionNetflix.GestionNetflix.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Director {
    @Id @GeneratedValue
    private Long id;
    private String nombre;
    private Integer anioNacimiento;

    @OneToMany(mappedBy = "Director" )
    private List<Pelicula> peliculas = new ArrayList <>();

}
