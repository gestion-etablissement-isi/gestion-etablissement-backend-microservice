package com.isi.professeur.professeur;

import jakarta.validation.constraints.NotNull;

public record ProfesseurRequest(
        String id,
        @NotNull(message = "Le nom du professeur est requis")
        String nom,
        @NotNull(message = "Le prénom du professeur est requis")
        String prenom,
        @NotNull(message = "L'email du professeur est requis")
        String email,
        @NotNull(message = "Le statut du professeur est requis")
        String statut,
        @NotNull(message = "La matiere du professeur est requise")
        String matiereId

) {
}

