package com.example.GestionNetflix.GestionNetflix.controllers;

import com.example.GestionNetflix.GestionNetflix.dtos.ActorRequestDto;
import com.example.GestionNetflix.GestionNetflix.dtos.GetActorList;
import com.example.GestionNetflix.GestionNetflix.services.ActorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.GestionNetflix.GestionNetflix.model.Actor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/actor")
@Validated
@Tag(name = "Actores", description = "Gestión de los actores y actrices (crear y listar)")
public class ActorController {

    private final ActorService actorService;

    @Operation(
            summary = "Listar todos los actores",
            description = "Devuelve todos los actores y actrices registrados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de actores",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetActorList.class)),
                            examples = @ExampleObject(
                                    name = "Listado de actores",
                                    value = """
                                            [
                                              { "id": 1, "nombre": "Leonardo DiCaprio" },
                                              { "id": 2, "nombre": "Elliot Page" }
                                            ]
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hay actores registrados",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 404",
                                    value = """
                                            {
                                              "type": "https://example.com/errors/not-found",
                                              "title": "Recurso no encontrado",
                                              "status": 404,
                                              "detail": "No hay actores registrados",
                                              "instance": "/api/v1/actores"
                                            }
                                            """
                            )
                    )
            )
    })

    @GetMapping
    public ResponseEntity<List<GetActorList>> GetAllActors() {
        List<Actor> result = actorService.getAllActor();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetActorList::of)
                        .toList());
    }

    @Operation(
            summary = "Crear un actor",
            description = "Crea un nuevo actor o actriz a partir de su nombre"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Actor creado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetActorList.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada no válidos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 400",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Bad Request",
                                              "status": 400,
                                              "detail": "Error de validación",
                                              "instance": "/api/v1/actores",
                                              "invalid-params": [
                                                {
                                                  "object": "actorRequestDTO",
                                                  "message": "El nombre del actor es obligatorio",
                                                  "field": "nombre",
                                                  "rejectedValue": ""
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<GetActorList> addActor(
            @Valid @RequestBody ActorRequestDto dto) {
        return ResponseEntity.status(201)
                .body(GetActorList.of(actorService.addActor(dto)));
    }

}
