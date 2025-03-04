package com.isi.institution.professeur;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ProfesseurRequest(
        Long id,
        @NotNull(message = "Le prénom de l'étudiant est obligatoire")
        String nom,
        @NotNull(message = "Le nom de l'étudiant est obligatoire")
        String prenom,
        @NotNull(message = "L'email de l'étudiant est obligatoire")
        @Email(message = "L'email n'est pas valide")
        String email,
        @NotNull(message = "Le numéro de téléphone est obligatoire")
        String tel,
        @NotNull(message = "Le specialite est obligatoire")
        String specialite
) {
}
