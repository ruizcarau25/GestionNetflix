package com.example.GestionNetflix.GestionNetflix.repos;

import com.example.GestionNetflix.GestionNetflix.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

    boolean existsByTitulo(String titulo);

}
