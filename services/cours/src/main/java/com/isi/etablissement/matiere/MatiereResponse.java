package com.isi.etablissement.matiere;

import jakarta.validation.constraints.NotNull;

public record MatiereResponse(
        String id,
        String libelle,
        String statut
) {
}
