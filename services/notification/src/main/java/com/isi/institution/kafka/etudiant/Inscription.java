package com.isi.institution.kafka.etudiant;

public record Inscription(
        String etudiantId,
        String classeId,
        String anneeScolaire,
        String email,
        String nom,
        String prenom
) {
}
