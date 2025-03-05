package com.isi.institution.inscription;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inscriptions")
@RequiredArgsConstructor
public class InscriptionController {
    private final InscriptionService service;

    @PostMapping
    public ResponseEntity<InscriptionResponse> inscrire(
            @Valid @RequestBody InscriptionRequest request
    ) {
        InscriptionResponse inscription = service.inscrire(request);
        return new ResponseEntity<>(inscription, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InscriptionResponse>> getAllInscriptions() {
        List<InscriptionResponse> inscriptions = service.getAllInscriptions();
        return ResponseEntity.ok(inscriptions);
    }

    @GetMapping("/{inscription-id}")
    public ResponseEntity<InscriptionResponse> getInscriptionById(
            @PathVariable("inscription-id") String id
    ) {
        InscriptionResponse inscription = service.getInscriptionById(id);
        return ResponseEntity.ok(inscription);
    }

    @DeleteMapping("/{inscription-id}")
    public ResponseEntity<Void> deleteInscription(
            @PathVariable("inscription-id") String id
    ) {
        service.deleteInscription(id);
        return ResponseEntity.noContent().build();
    }
}