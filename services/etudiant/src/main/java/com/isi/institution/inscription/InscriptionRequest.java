package com.isi.institution.inscription;

import jakarta.validation.constraints.NotNull;

public record InscriptionRequest(

        String etudiantId,
        @NotNull(message = "La classe  est obligatoire")

        String classeId,
        @NotNull(message = "L' annee scolaire est obligatoire")

        String anneeScolaire

) {
}
