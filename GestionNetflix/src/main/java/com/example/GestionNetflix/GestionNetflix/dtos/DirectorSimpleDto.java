package com.example.GestionNetflix.GestionNetflix.dtos;

import com.example.GestionNetflix.GestionNetflix.model.Director;

public record DirectorSimpleDto(Long id, String nombre) {
    public static DirectorSimpleDto of(Director d) {
        return new DirectorSimpleDto(d.getId(), d.getNombre());
    }
}
