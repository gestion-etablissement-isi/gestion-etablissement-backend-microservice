package com.isi.institution.classe;

import jakarta.validation.constraints.NotNull;

public record ClasseRequest(
        String id,
        @NotNull(message = "Le nom de la classe est obligatoire")
        String nom,
        @NotNull(message = "L' annee scolaire est obligatoire")
        String annee_scolaire

) {
}
