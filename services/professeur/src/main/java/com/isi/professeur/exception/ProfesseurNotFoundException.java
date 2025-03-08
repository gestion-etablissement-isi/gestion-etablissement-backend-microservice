package com.isi.professeur.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProfesseurNotFoundException extends RuntimeException {
    private final String msg;
}

