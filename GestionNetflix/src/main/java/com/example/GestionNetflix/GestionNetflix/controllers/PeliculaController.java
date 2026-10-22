package com.example.GestionNetflix.GestionNetflix.controllers;

import com.example.GestionNetflix.GestionNetflix.dtos.DirectorRequestDto;
import com.example.GestionNetflix.GestionNetflix.dtos.GetDirectorList;
import com.example.GestionNetflix.GestionNetflix.dtos.GetPeliculaList;
import com.example.GestionNetflix.GestionNetflix.dtos.PeliculaRequestDto;
import com.example.GestionNetflix.GestionNetflix.model.Pelicula;
import com.example.GestionNetflix.GestionNetflix.services.PeliculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pelicula")
public class PeliculaController {

    private  final PeliculaService peliculaService;

    @Operation(
            summary = "Listar todas las películas",
            description = "Devuelve todas las películas, cada una con su director y su reparto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de películas",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPeliculaList.class)),
                            examples = @ExampleObject(
                                    name = "Listado de películas",
                                    value = """
                                            [
                                              {
                                                "id": 1,
                                                "titulo": "Origen",
                                                "genero": "Ciencia ficción",
                                                "fechaEstreno": "2010-07-16",
                                                "director": { "id": 1, "nombre": "Christopher Nolan" },
                                                "actores": [
                                                  { "id": 1, "nombre": "Leonardo DiCaprio" }
                                                ]
                                              }
                                            ]
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No hay películas registradas",
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
                                              "detail": "No hay películas registradas",
                                              "instance": "/api/v1/peliculas"
                                            }
                                            """
                            )
                    )
            )
    })

    @GetMapping
    public ResponseEntity<List<GetPeliculaList>> GetAllPeliculas(){
        List<Pelicula> result = peliculaService.GetAllPeliculas();

        return ResponseEntity.ok(
                result
                        .stream()
                        .map(GetPeliculaList::of)
                        .toList()
        );
    }

    @Operation(
            summary = "Crear una película",
            description = "Crea una película asociada a un director existente (directorId). "
                    + "El título debe ser único y el director debe tener al menos 18 años en el año de estreno."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Película creada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetPeliculaList.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada no válidos (campos obligatorios, o director menor de 18 años en el estreno)",
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
                                              "instance": "/api/v1/peliculas",
                                              "invalid-params": [
                                                {
                                                  "object": "peliculaRequestDTO",
                                                  "message": "El director debe tener al menos 18 años en el año de estreno de la película",
                                                  "field": "fechaEstreno",
                                                  "rejectedValue": "1980-01-01"
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "El director indicado en directorId no existe",
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
                                              "detail": "Director no encontrado con id 99",
                                              "instance": "/api/v1/peliculas"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya existe una película con ese título",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 409",
                                    value = """
                                            {
                                              "type": "https://example.com/errors/pelicula-ya-existe",
                                              "title": "La película ya existe",
                                              "status": 409,
                                              "detail": "Ya existe una película con el título 'Origen'",
                                              "instance": "/api/v1/peliculas"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<GetPeliculaList> addPelicula(
            @Valid @RequestBody PeliculaRequestDto dto) {
        return ResponseEntity.status(201)
                .body(GetPeliculaList.of(peliculaService.addPelicula(dto)));
    }

}
