package com.isi.institution.matiere;

import jakarta.validation.constraints.NotNull;

public record MatiereRequest(
        String id,
        @NotNull(message = "Le libelle de la matiere est requis")
        String libelle,
        @NotNull(message = "Le statut de la matiere est requis")
        String statut
) {
}
