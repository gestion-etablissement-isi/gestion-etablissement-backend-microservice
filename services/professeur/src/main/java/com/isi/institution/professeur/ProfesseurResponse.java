package com.isi.institution.professeur;

public record ProfesseurResponse(
        String id,
        String nom,
        String prenom,
        String email,
        String tel,
        String specialite
) {
}
