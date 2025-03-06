package com.isi.institution.exception;

import lombok.Getter;

@Getter
public class ClasseNotFoundException extends RuntimeException {
    public ClasseNotFoundException(String message) {
        super(message);
    }
}
