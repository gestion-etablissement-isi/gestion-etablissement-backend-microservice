package com.isi.institution.inscription;

public record InscriptionRequest(
        String etudiantId,
        String classeId,
        String anneeScolaire
) {}