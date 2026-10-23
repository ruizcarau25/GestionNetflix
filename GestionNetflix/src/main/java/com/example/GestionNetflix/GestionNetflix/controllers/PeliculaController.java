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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/peliculas")
@Validated
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

    @Operation(
            summary = "Modificar una película",
            description = "Modifica los datos de una película existente (el reparto no se modifica). "
                    + "Se aplican las mismas reglas que al crearla."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Película modificada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetPeliculaList.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada no válidos",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe la película o el director indicado",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Otra película ya usa ese título",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PutMapping("/{peliculaId}")
    public ResponseEntity<GetPeliculaList> updatePelicula(
            @Parameter(description = "Id de la película", example = "1")
            @PathVariable @Min(value = 1, message = "El id debe ser mayor o igual que 1") Long peliculaId,
            @Valid @RequestBody PeliculaRequestDto dto) {
        return ResponseEntity.ok(
                GetPeliculaList.of(peliculaService.updatePelicula(peliculaId, dto)));
    }

    @Operation(
            summary = "Borrar una película",
            description = "Borra una película y su asignación de reparto. "
                    + "La operación es idempotente: si la película no existe también devuelve 204."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Película borrada (o inexistente)"),
            @ApiResponse(
                    responseCode = "400",
                    description = "El id no es válido (debe ser mayor o igual que 1)",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @DeleteMapping("/{peliculaId}")
    public ResponseEntity<Void> deletePelicula(
            @Parameter(description = "Id de la película", example = "1")
            @PathVariable @Min(value = 1, message = "El id debe ser mayor o igual que 1") Long peliculaId) {
        peliculaService.deletePelicula(peliculaId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obtener una película por su id",
            description = "Devuelve una película con su director y la lista de su reparto de actores"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Película encontrada, incluyendo su reparto",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetPeliculaList.class),
                            examples = @ExampleObject(
                                    name = "Película con reparto",
                                    value = """
                                            {
                                              "id": 1,
                                              "titulo": "Origen",
                                              "genero": "Ciencia ficción",
                                              "fechaEstreno": "2010-07-16",
                                              "director": { "id": 1, "nombre": "Christopher Nolan" },
                                              "actores": [
                                                { "id": 1, "nombre": "Leonardo DiCaprio" },
                                                { "id": 2, "nombre": "Elliot Page" }
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "El id no es válido (debe ser mayor o igual que 1)",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe ninguna película con ese id",
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
                                              "detail": "Película no encontrada con id 99",
                                              "instance": "/api/v1/peliculas/99"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/{peliculaId}")
    public ResponseEntity<GetPeliculaList> getPeliculaById(
            @Parameter(description = "Id de la película", example = "1")
            @PathVariable @Min(value = 1, message = "El id debe ser mayor o igual que 1") Long peliculaId) {
        return ResponseEntity.ok(GetPeliculaList.of(peliculaService.getPeliculaById(peliculaId)));
    }

    @Operation(
            summary = "Asignar un actor a una película",
            description = "Añade un actor existente al reparto de una película existente (relación Muchos a Muchos). "
                    + "Devuelve la película con el reparto actualizado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Actor asignado. Se devuelve la película con su reparto actualizado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetPeliculaList.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Alguno de los ids no es válido (deben ser mayores o iguales que 1)",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe la película o el actor",
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
                                              "detail": "Actor no encontrado con id 99",
                                              "instance": "/api/v1/peliculas/1/actores/99"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El actor ya forma parte del reparto de la película",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Error 409",
                                    value = """
                                            {
                                              "type": "https://example.com/errors/actor-ya-en-reparto",
                                              "title": "El actor ya está en el reparto",
                                              "status": 409,
                                              "detail": "El actor con id 1 ya forma parte del reparto de la película con id 1",
                                              "instance": "/api/v1/peliculas/1/actores/1"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/{peliculaId}/actores/{actorId}")
    public ResponseEntity<GetPeliculaList> asignarActor(
            @Parameter(description = "Id de la película", example = "1")
            @PathVariable @Min(value = 1, message = "El id debe ser mayor o igual que 1") Long peliculaId,
            @Parameter(description = "Id del actor", example = "1")
            @PathVariable @Min(value = 1, message = "El id debe ser mayor o igual que 1") Long actorId) {
        return ResponseEntity.ok(
                GetPeliculaList.of(peliculaService.asignarActor(peliculaId, actorId)));
    }

}
