package com.isi.institution.kafka.etudiant;

import java.time.LocalDateTime;

public record InscriptionConfirmation(
        String etudiantId,
        String classeId,
        String anneeScolaire,
        String email,
        String nom,
        String prenom,
        LocalDateTime dateInscription,
        boolean confirmed
) {
}