package com.isi.institution.inscription;

public record InscriptionResponse(
        String id,
        String etudiantId,
        String classeId,
        String anneeScolaire
) {
}
