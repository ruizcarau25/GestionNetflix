package com.example.GestionNetflix.GestionNetflix.dtos;

import com.example.GestionNetflix.GestionNetflix.model.Director;
import com.example.GestionNetflix.GestionNetflix.model.Pelicula;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        name = "DirectorResponseDTO",
        description = "Datos de un director"
)

public record GetDirectorList( @Schema(description = "Nombre del director") String nombre,
                               @Schema(description = "Año de nacimiento del director") Integer anioNacimiento) {
    public static GetDirectorList of(Director d) {
        return new GetDirectorList(
                d.getNombre(),
                d.getAnioNacimiento()
        );
    }

}
