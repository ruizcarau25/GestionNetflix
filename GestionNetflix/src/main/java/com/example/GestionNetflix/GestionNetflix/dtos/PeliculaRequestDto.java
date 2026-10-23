package com.example.GestionNetflix.GestionNetflix.dtos;

import com.example.GestionNetflix.GestionNetflix.model.Pelicula;
import com.example.GestionNetflix.GestionNetflix.validation.DirectorMayorDeEdad;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(
        name = "PeliculaRequestDTO",
        description = "Datos necesarios para crear o modificar una película. ")
@DirectorMayorDeEdad(message = "{peliculaRequestDto.directorMayorDeEdad}")
public record PeliculaRequestDto(

        @Schema(description = "Título de la película (único)")
        @NotBlank(message = "{peliculaRequestDto.titulo.notBlank}")
        @Size(max = 150, message = "{peliculaRequestDto.titulo.size}")
        String titulo,

        @Schema(description = "Género de la película")
        @NotBlank(message = "{peliculaRequestDto.genero.notBlank}")
        @Size(max = 50, message = "{peliculaRequestDto.genero.size}")
        String genero,

        @Schema(description = "Fecha de estreno (formato yyyy-MM-dd)")
        @NotNull(message = "{peliculaRequestDto.fechaEstreno.notNull}")
        LocalDate fechaEstreno,

        @Schema(description = "Id de un director existente")
        @NotNull(message = "{peliculaRequestDto.directorId.notNull}")
        @Positive(message = "{peliculaRequestDto.directorId.positive}")
        Long directorId
) {

    public Pelicula to() {
        return Pelicula.builder()
                .titulo(titulo)
                .genero(genero)
                .fechaEstreno(fechaEstreno)
                .build();
    }

}
