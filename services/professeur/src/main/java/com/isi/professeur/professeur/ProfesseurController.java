package com.isi.professeur.professeur;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/professeurs")
@RequiredArgsConstructor
public class ProfesseurController {
    private final ProfesseurService service;

    @PostMapping
    public ResponseEntity<String> createProfesseur(
            @RequestBody @Valid ProfesseurRequest request
    ) {

        return ResponseEntity.ok(service.createProfesseur(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateProfesseur(
            @RequestBody @Valid ProfesseurRequest request
    ) {
        service.updateProfesseur(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<ProfesseurResponse>> findAll() {
        return ResponseEntity.ok(service.findAllProfesseurs());
    }

    @GetMapping("/exist/{professeur-id}")
    public ResponseEntity<Boolean> existById(
            @PathVariable("professeur-id") String professeurId
    ) {
        return ResponseEntity.ok(service.existById(professeurId));
    }

    @GetMapping("/{professeur-id}")
    public ResponseEntity<ProfesseurResponse> findById(
            @PathVariable("professeur-id") String professeurId
    ) {
        return ResponseEntity.ok(service.findById(professeurId));
    }

    @DeleteMapping("/{professeur-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("professeur-id") String professeurId
    ) {
        service.deleteProfesseur(professeurId);
        return ResponseEntity.accepted().build();
    }
}
