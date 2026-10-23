package com.example.GestionNetflix.GestionNetflix.dtos;


import com.example.GestionNetflix.GestionNetflix.model.Director;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(
        name = "DirectorRequestDto",
        description = "Datos necesarios para crear o modificar un director"
)
public record DirectorRequestDto(
        @Schema(description = "Nombre del director")
        @NotBlank(message = "{directorRequestDTO.nombre.notBlank}")
        @Size(max = 100, message = "{directorRequestDTO.nombre.size}")
        String nombre,

        @Schema(description = "Año de nacimiento del director")
        @NotNull(message = "{directorRequestDTO.anioNacimiento.notNull}")
        @Min(value = 1910, message = "{directorRequestDTO.anioNacimiento.min}")
        Integer anioNacimiento
) {
    public Director to() {
        return Director.builder()
                .nombre(nombre)
                .anioNacimiento(anioNacimiento)
                .build();
    }
}
