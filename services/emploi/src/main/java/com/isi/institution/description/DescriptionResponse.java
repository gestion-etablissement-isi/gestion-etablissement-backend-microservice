package com.isi.institution.description;

import java.time.LocalDate;

public record DescriptionResponse(
        String id,
        LocalDate dateCours,
        String heureDebut,
        String heureFin,
        String description
) {
}
