package com.isi.institution.exception;

import lombok.Getter;

@Getter
public class EtudiantExistException extends RuntimeException {
    public EtudiantExistException(String message) {
        super(message);
    }
}
