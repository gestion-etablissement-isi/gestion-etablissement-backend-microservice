package com.isi.institution.cours;


public record CoursResponse (
        String id,
        String titre,
        int volumeHoraire,
        int coefficient,
        String anneeAcademique,
        String classeId,
        String professeurId,
        String matiereId
) {
}
