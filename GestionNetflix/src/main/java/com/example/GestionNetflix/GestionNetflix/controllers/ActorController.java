package com.example.GestionNetflix.GestionNetflix.controllers;

import com.example.GestionNetflix.GestionNetflix.dtos.GetActorList;
import com.example.GestionNetflix.GestionNetflix.services.ActorService;
import lombok.RequiredArgsConstructor;
import com.example.GestionNetflix.GestionNetflix.model.Actor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/actor")
public class ActorController {

    private final ActorService actorService;

    @GetMapping
    public ResponseEntity<List<GetActorList>> GetAllActors() {
        List<Actor> result = actorService.getAllActor();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetActorList::of)
                        .toList());
    }

}
