package com.example.GestionNetflix.GestionNetflix.controllers;

import com.example.GestionNetflix.GestionNetflix.dtos.DirectorRequestDto;
import com.example.GestionNetflix.GestionNetflix.dtos.GetDirectorList;
import com.example.GestionNetflix.GestionNetflix.model.Director;
import com.example.GestionNetflix.GestionNetflix.services.DirectorService;
import com.example.GestionNetflix.GestionNetflix.services.PeliculaService;
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
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/director")
@Validated
@Tag(name = "Directores", description = "Gestión de los directores de las películas (CRUD completo)")
public class DirectorController {

    private final DirectorService directorService;
    private final PeliculaService peliculaService;

    @Operation(
            summary = "Listar todos los directores",
            description = "Devuelve todos los directores registrados"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de directores",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetDirectorList.class)),
                            examples = @ExampleObject(
                                    name = "Listado de directores",
                                    value = """
                                            [
                                              { "id": 1, "nombre": "Christopher Nolan", "anioNacimiento": 1970 },
                                              { "id": 2, "nombre": "Greta Gerwig", "anioNacimiento": 1983 }
                                            ]
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hay directores registrados",
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
                                              "detail": "No hay directores registrados",
                                              "instance": "/api/v1/directores"
                                            }
                                            """
                            )
                    )
            )
    })

    @RequestMapping
    public ResponseEntity<List<GetDirectorList>> GetAllDirector(){
        List<Director> result = directorService.GetAllDirector();
        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetDirectorList::of)
                        .toList()
        );
    }

    @Operation(
            summary = "Crear un director",
            description = "Crea un nuevo director a partir de su nombre y su año de nacimiento"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Director creado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetDirectorList.class)
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
                                              "instance": "/api/v1/directores",
                                              "invalid-params": [
                                                {
                                                  "object": "directorRequestDTO",
                                                  "message": "El nombre del director es obligatorio",
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
    public ResponseEntity<GetDirectorList> addDirector(
            @Valid @RequestBody DirectorRequestDto dto) {
        return ResponseEntity.status(201)
                .body(GetDirectorList.of(directorService.addDirector(dto)));
    }

}
