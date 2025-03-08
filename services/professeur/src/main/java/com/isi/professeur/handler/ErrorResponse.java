package com.isi.professeur.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
