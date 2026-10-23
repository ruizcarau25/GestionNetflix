# GestionNetflix

API REST hecha con Spring Boot para gestionar películas, directores y actores. Usa H2 en memoria, así que no hace falta instalar ninguna base de datos.

## Cómo arrancarlo

Necesitas Java 21. Desde la carpeta donde está el `pom.xml`:

```bash
./mvnw spring-boot:run
```

En Windows: `mvnw.cmd spring-boot:run`

Se levanta en el puerto 8080. Una vez arrancado:

- Swagger: http://localhost:8080/swagger-ui.html
- Consola de H2: http://localhost:8080/h2-console (URL `jdbc:h2:mem:gestionnetflix`, usuario `sa`, sin contraseña)

Cada vez que arranca, la base de datos se crea desde cero y `DataSeed` mete unos cuantos datos de prueba (3 directores, 8 actores y 5 películas). Si paras la app, se pierde todo lo que hayas creado.

## Modelo

Un director puede tener varias películas, y una película tiene un solo director. Entre películas y actores la relación es muchos a muchos (tabla `pelicula_actor`, la dueña de la relación es `Pelicula`).

- Director: id, nombre, año de nacimiento
- Película: id, título (único), género, fecha de estreno, director
- Actor: id, nombre

## Endpoints

Todos cuelgan de `/api/v1`.

**Directores** (`/directores`)
- `GET /directores` lista todos
- `GET /directores/{id}` uno por id
- `POST /directores` crea
- `PUT /directores/{id}` modifica
- `DELETE /directores/{id}` borra

**Películas** (`/peliculas`)
- `GET /peliculas` lista todas, con su director y su reparto
- `GET /peliculas/{id}` una por id
- `POST /peliculas` crea
- `PUT /peliculas/{id}` modifica (el reparto no se toca)
- `DELETE /peliculas/{id}` borra
- `POST /peliculas/{peliculaId}/actores/{actorId}` añade un actor al reparto

**Actores** (`/actores`)
- `GET /actores` lista todos
- `POST /actores` crea

Ejemplo para crear una película:

```json
{
  "titulo": "Origen",
  "genero": "Ciencia ficción",
  "fechaEstreno": "2010-07-16",
  "directorId": 1
}
```

Y lo que devuelve:

```json
{
  "id": 1,
  "titulo": "Origen",
  "genero": "Ciencia ficción",
  "fechaEstreno": "2010-07-16",
  "director": { "id": 1, "nombre": "Christopher Nolan" },
  "actores": []
}
```

## Validaciones

Los campos obligatorios y las longitudes máximas (100 caracteres para nombres, 150 para el título, 50 para el género) se validan en los DTOs. Además:

- El año de nacimiento del director tiene que ser 1910 o posterior.
- El título de una película no se puede repetir.
- Los ids de la ruta tienen que ser 1 o más.
- El director tiene que tener al menos 18 años en el año de estreno de la película. Esto está hecho con una anotación propia, `@DirectorMayorDeEdad`, y el error sale asociado al campo `fechaEstreno`.

Los textos de los mensajes están en `src/main/resources/messages/errors.properties`.

## Errores

Se devuelven como `ProblemDetail` desde `GlobalExceptionHandler`:

- 400: datos mal enviados. Trae una lista `invalid-params` con el campo, el valor rechazado y el mensaje.
- 404: no existe la película, el director o el actor. También salta si pides un listado y no hay nada.
- 409: título de película repetido, o actor que ya estaba en el reparto.

## Estructura

```
config/        mensajes de validación y validador
controllers/   endpoints y anotaciones de Swagger
dtos/          los *RequestDto (entrada) y los Get*List / *SimpleDto (salida)
error/         excepciones propias y el manejador global
model/         entidades JPA
repos/         repositorios
services/      lógica
utils/         DataSeed
validation/    la validación del director mayor de edad
```