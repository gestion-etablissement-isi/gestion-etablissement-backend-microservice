package com.isi.etablissement.cours;

import com.isi.etablissement.matiere.Matiere;



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
