package com.isi.institution.description;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DescriptionRequest(
        String id,
        @NotNull(message = "La date est requise")
        @JsonFormat(pattern = "yyyy-MM-dd")  // Format pour la date au format JSON
        LocalDate dateCours,
        @NotBlank(message = "L'heure de debut est requis")
        String heureDebut,
        @NotBlank(message = "L'heure de fin est requis")
        String heureFin,
        @NotBlank(message = "La description est requis")
        String description
) {
}

