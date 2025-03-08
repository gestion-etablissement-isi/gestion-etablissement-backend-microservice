package com.isi.institution.creneau;

import com.isi.institution.description.Description;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreneauResponse(
        String id,
        String coursId,
        List<Description> descriptions
) {
}
