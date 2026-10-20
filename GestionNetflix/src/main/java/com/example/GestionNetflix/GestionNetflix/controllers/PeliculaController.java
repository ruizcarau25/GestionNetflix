package com.example.GestionNetflix.GestionNetflix.controllers;

import com.example.GestionNetflix.GestionNetflix.dtos.GetPeliculaList;
import com.example.GestionNetflix.GestionNetflix.model.Pelicula;
import com.example.GestionNetflix.GestionNetflix.services.PeliculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pelicula")
public class PeliculaController {

    private  final PeliculaService peliculaService;

    @GetMapping
    public ResponseEntity<List<GetPeliculaList>> GetAllPeliculas(){
        List<Pelicula> result = peliculaService.GetAllPeliculas();

        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetPeliculaList::of)
                        .toList()
        );
    }

}
