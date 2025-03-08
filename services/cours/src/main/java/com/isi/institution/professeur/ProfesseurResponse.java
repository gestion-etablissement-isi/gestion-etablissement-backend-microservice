package com.isi.institution.professeur;

public record ProfesseurResponse(
        String id,
        String nom,
        String prenom,
        String email,
        String matiere,
        String statut
) {
}
