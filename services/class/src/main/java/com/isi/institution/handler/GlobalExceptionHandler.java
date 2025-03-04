package com.isi.institution.handler;

import com.isi.institution.exception.ClasseNotFoundException;
import com.isi.institution.exception.SalleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère l'exception lorsque la classe n'est pas trouvée
     */
    @ExceptionHandler(ClasseNotFoundException.class)
    public ResponseEntity<Map<String, String>> handle(ClasseNotFoundException exp) {
        Map<String, String> response = new HashMap<>();
        response.put("message", exp.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(SalleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handle(SalleNotFoundException exp) {
        Map<String, String> response = new HashMap<>();
        response.put("message", exp.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    /**
     * Gère les erreurs de validation des arguments des requêtes
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exp) {
        Map<String, String> errors = new HashMap<>();

        exp.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }
}
