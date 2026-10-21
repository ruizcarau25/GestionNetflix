package com.example.GestionNetflix.GestionNetflix.dtos;

import com.example.GestionNetflix.GestionNetflix.model.Actor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "ActorRequestDto",
        description = "Datos necesarios para crear un actor"
)

public record ActorRequestDto(
        @Schema(description = "Nombre del actor o actriz")
        @NotBlank(message = "{actorRequestDTO.nombre.notBlank}")
        @Size(max = 100, message = "{actorRequestDTO.nombre.size}")
        String nombre
) {
    public Actor to() {
        return Actor.builder()
                .nombre(nombre)
                .build();
    }
}
