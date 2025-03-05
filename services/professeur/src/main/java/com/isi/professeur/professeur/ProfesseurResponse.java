package com.isi.professeur.professeur;

public record ProfesseurResponse(
        String id,
        String nom,
        String prenom,
        String email,
        String statut,
        String matiereId
) {
}
