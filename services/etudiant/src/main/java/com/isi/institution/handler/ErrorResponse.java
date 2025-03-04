package com.isi.institution.handler;

import java.util.HashMap;

public class ErrorResponse {

    private final HashMap<String, String> errors;

    public ErrorResponse(HashMap<String, String> errors) {
        this.errors = errors;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }
}