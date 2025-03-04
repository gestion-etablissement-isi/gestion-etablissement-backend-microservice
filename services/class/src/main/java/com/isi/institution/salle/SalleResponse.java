package com.isi.institution.salle;

import jakarta.validation.constraints.NotNull;

public record SalleResponse(
        String id,
        String nom
) {
}
