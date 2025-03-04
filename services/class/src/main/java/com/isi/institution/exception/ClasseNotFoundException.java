package com.isi.institution.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClasseNotFoundException extends RuntimeException {
    private final String msg;
}
