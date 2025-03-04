package com.isi.institution.salle;

import jakarta.validation.constraints.NotNull;

public record SalleRequest(
        String id,
        @NotNull(message = "Le nom de la classe est obligatoire")
        String nom
) {
}
