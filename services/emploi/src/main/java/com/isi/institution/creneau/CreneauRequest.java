package com.isi.institution.creneau;

import com.isi.institution.description.Description;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreneauRequest(
        String id,
        @NotBlank(message = "Le cours du creneau est requise")
        String coursId,
        @NotEmpty(message = "La liste de descriptions du cours ne doit pas etre vide")
        List<Description> descriptions
) {
}
