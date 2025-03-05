package com.isi.institution.etudiant;

public record EtudiantResponse(
        String id,
        String nom,
        String prenom,
        String email,
        String tel
) {
}
