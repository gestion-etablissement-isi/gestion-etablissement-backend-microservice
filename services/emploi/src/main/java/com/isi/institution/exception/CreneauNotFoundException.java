package com.isi.institution.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreneauNotFoundException extends RuntimeException {
    private final String msg;
}
