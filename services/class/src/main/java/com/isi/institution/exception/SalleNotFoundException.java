package com.isi.institution.exception;

import lombok.Getter;

@Getter
public class SalleNotFoundException extends RuntimeException {

    public SalleNotFoundException(String message) {
        super(message);
    }
}
