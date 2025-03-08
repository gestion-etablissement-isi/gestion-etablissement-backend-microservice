package com.isi.institution.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MatiereNotFoundException extends RuntimeException{
    private final String msg;
}

