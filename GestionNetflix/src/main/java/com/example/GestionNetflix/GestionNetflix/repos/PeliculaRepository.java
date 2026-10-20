package com.example.GestionNetflix.GestionNetflix.repos;

import model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

}
