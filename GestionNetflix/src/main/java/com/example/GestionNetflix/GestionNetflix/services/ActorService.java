package com.example.GestionNetflix.GestionNetflix.services;

import com.example.GestionNetflix.GestionNetflix.dtos.ActorRequestDto;
import com.example.GestionNetflix.GestionNetflix.error.notfound.ActorNoEncontradoException;
import com.example.GestionNetflix.GestionNetflix.repos.ActorRepository;
import com.example.GestionNetflix.GestionNetflix.repos.PeliculaRepository;
import lombok.RequiredArgsConstructor;
import com.example.GestionNetflix.GestionNetflix.model.Actor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final PeliculaRepository peliculaRepository;

    public List<Actor> getAllActor() {
        List<Actor> result = actorRepository.findAll();
        if (result.isEmpty()) {
            throw new ActorNoEncontradoException();
        }
        return result;
    }

    public Actor addActor(ActorRequestDto dto) {
        return actorRepository.save(dto.to());
    }

}
