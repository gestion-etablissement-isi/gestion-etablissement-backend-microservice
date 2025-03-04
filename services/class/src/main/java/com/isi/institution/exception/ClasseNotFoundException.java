package com.isi.institution.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public class ClasseNotFoundException extends RuntimeException {
    public ClasseNotFoundException(String message) {
        super(message);
    }
}
