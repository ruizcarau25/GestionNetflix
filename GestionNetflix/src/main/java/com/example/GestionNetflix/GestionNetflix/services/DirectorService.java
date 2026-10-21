package com.example.GestionNetflix.GestionNetflix.services;

import com.example.GestionNetflix.GestionNetflix.dtos.DirectorRequestDto;
import com.example.GestionNetflix.GestionNetflix.model.Actor;
import com.example.GestionNetflix.GestionNetflix.model.Director;
import com.example.GestionNetflix.GestionNetflix.repos.DirectorRepository;
import com.example.GestionNetflix.GestionNetflix.repos.PeliculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final PeliculaRepository peliculaRepository;

    public List<Director> GetAllDirector() {
        List<Director> result = directorRepository.findAll();
        if (result.isEmpty()){
            throw new RuntimeException();
        }
        return result;
    }

    public Director addDirector(DirectorRequestDto dto) {
        return directorRepository.save(dto.to());
    }

}
