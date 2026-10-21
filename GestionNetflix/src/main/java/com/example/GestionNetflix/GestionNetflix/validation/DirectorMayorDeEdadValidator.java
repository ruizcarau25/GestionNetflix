package com.example.GestionNetflix.GestionNetflix.validation;

import com.example.GestionNetflix.GestionNetflix.dtos.PeliculaRequestDto;
import com.example.GestionNetflix.GestionNetflix.repos.DirectorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class DirectorMayorDeEdadValidator
        implements ConstraintValidator<DirectorMayorDeEdad, PeliculaRequestDto> {

    private static final int EDAD_MINIMA = 18;

    @Autowired
    private DirectorRepository directorRepository;

    @Override
    public boolean isValid(PeliculaRequestDto dto, ConstraintValidatorContext context) {

        if (dto == null || dto.directorId() == null || dto.fechaEstreno() == null) {
            return true;
        }

        return directorRepository.findById(dto.directorId())
                .map(director -> {
                    int edadEnElEstreno = dto.fechaEstreno().getYear() - director.getAnioNacimiento();
                    boolean valido = edadEnElEstreno >= EDAD_MINIMA;

                    if (!valido) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate(
                                        context.getDefaultConstraintMessageTemplate())
                                .addPropertyNode("fechaEstreno")
                                .addConstraintViolation();
                    }
                    return valido;
                })
                .orElse(true);
    }

}

