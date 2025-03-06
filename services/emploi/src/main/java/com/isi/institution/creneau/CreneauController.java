package com.isi.institution.creneau;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/creneau")
@RequiredArgsConstructor
public class CreneauController {
    private final CreneauService service;

    @PostMapping
    public ResponseEntity<String> createCreneau(
            @RequestBody @Valid CreneauRequest request
    ) {
        return ResponseEntity.ok(service.createCreneau(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateCreneau(
            @RequestBody @Valid CreneauRequest request
    ) {
        service.updateCreneau(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CreneauResponse>> findAll() {
        return ResponseEntity.ok(service.findAllCreneau());
    }

    @GetMapping("/exist/{creneau-id}")
    public ResponseEntity<Boolean> existById(
            @PathVariable("creneau-id") String creneauId
    ) {
        return ResponseEntity.ok(service.existById(creneauId));
    }

    @GetMapping("/{creneau-id}")
    public ResponseEntity<CreneauResponse> findById(
            @PathVariable("creneau-id") String creneauId
    ) {
        return ResponseEntity.ok(service.findById(creneauId));
    }

    @DeleteMapping("/{creneau-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("creneau-id") String creneauId
    ) {
        service.deleteCreneau(creneauId);
        return ResponseEntity.accepted().build();
    }
}
