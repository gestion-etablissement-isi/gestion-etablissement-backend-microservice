package com.isi.etablissement.cours;

import com.isi.etablissement.matiere.Matiere;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CoursRequest(
        String id,
        @NotNull(message = "Le titre du cours est requis")
        String titre,
        @NotNull(message = "Le volume horaire du cours est requis")
        int volumeHoraire,
        @NotNull(message = "Le coefficient du cours est requis")
        int coefficient,
        @NotNull(message = "L'année académique du cours est requis")
        String anneeAcademique,
        @NotBlank(message = "La classe du cours est requise")
        String classeId,
        @NotBlank(message = "Le professeur du cours est requis")
        String professeurId,
        @NotNull(message = "La matiere du cours est requise")
        String matiereId

) {
}
