package com.isi.institution.kafka;

import java.time.LocalDateTime;

public record InscriptionConfirmation(
        String inscriptionId,
        String etudiantId,
        String classeId,
        String anneeScolaire,
        LocalDateTime dateInscription
) {
}