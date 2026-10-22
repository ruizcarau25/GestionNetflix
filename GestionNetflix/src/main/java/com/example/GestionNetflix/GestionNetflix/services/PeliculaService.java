package com.example.GestionNetflix.GestionNetflix.services;

import com.example.GestionNetflix.GestionNetflix.dtos.PeliculaRequestDto;
import com.example.GestionNetflix.GestionNetflix.error.PeliculaYaExisteException;
import com.example.GestionNetflix.GestionNetflix.error.notfound.DirectorNoEncontradoException;
import com.example.GestionNetflix.GestionNetflix.error.notfound.PeliculaNoEncontradaException;
import com.example.GestionNetflix.GestionNetflix.model.Director;
import com.example.GestionNetflix.GestionNetflix.model.Pelicula;
import com.example.GestionNetflix.GestionNetflix.repos.ActorRepository;
import com.example.GestionNetflix.GestionNetflix.repos.DirectorRepository;
import com.example.GestionNetflix.GestionNetflix.repos.PeliculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeliculaService {

    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final PeliculaRepository peliculaRepository;

    public List<Pelicula> GetAllPeliculas() {
        List<Pelicula> result = peliculaRepository.findAll();
        if (result.isEmpty()) {
            //Cambiar por personalizada en un futuro
            throw new RuntimeException();
        }
        return result;
    }

    public Pelicula addPelicula(PeliculaRequestDto dto) {
        Director director = directorRepository.findById(dto.directorId())
                .orElseThrow(() -> new DirectorNoEncontradoException(dto.directorId()));

        if (peliculaRepository.existsByTitulo(dto.titulo())) {
            throw new PeliculaYaExisteException(dto.titulo());
        }

        Pelicula pelicula = dto.to();
        pelicula.setDirector(director);
        return peliculaRepository.save(pelicula);
    }

    public Pelicula updatePelicula(Long id, PeliculaRequestDto dto) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new PeliculaNoEncontradaException(id));

        Director director = directorRepository.findById(dto.directorId())
                .orElseThrow(() -> new DirectorNoEncontradoException(dto.directorId()));

        if (peliculaRepository.existsByTituloAndIdNot(dto.titulo(), id)) {
            throw new PeliculaYaExisteException(dto.titulo());
        }

        pelicula.setTitulo(dto.titulo());
        pelicula.setGenero(dto.genero());
        pelicula.setFechaEstreno(dto.fechaEstreno());
        pelicula.setDirector(director);
        return peliculaRepository.save(pelicula);
    }

    public void deletePelicula(Long id) {
        peliculaRepository.deleteById(id);
    }

    public Pelicula getPeliculaById(Long id) {
        return peliculaRepository.findById(id)
                .orElseThrow(() -> new PeliculaNoEncontradaException(id));
    }


}
