package com.example.GestionNetflix.GestionNetflix.controllers;

import com.example.GestionNetflix.GestionNetflix.dtos.GetDirectorList;
import com.example.GestionNetflix.GestionNetflix.model.Director;
import com.example.GestionNetflix.GestionNetflix.services.DirectorService;
import com.example.GestionNetflix.GestionNetflix.services.PeliculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/director")
public class DirectorController {

    private final DirectorService directorService;
    private final PeliculaService peliculaService;

    @RequestMapping
    public ResponseEntity<List<GetDirectorList>> GetAllDirector(){
        List<Director> result = directorService.GetAllDirector();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetDirectorList::of)
                        .toList()
        );
    }

}
