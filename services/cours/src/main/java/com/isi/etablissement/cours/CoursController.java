package com.isi.etablissement.cours;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cours")
@RequiredArgsConstructor
public class CoursController {
    private final CoursService service;

    @PostMapping
    public ResponseEntity<String> createCours(
            @RequestBody @Valid CoursRequest request
    ) {
        return ResponseEntity.ok(service.createCours(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateCours(
            @RequestBody @Valid CoursRequest request
    ) {
        service.updateCours(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CoursResponse>> findAll() {
        return ResponseEntity.ok(service.findAllCours());
    }

    @GetMapping("/exist/{cours-id}")
    public ResponseEntity<Boolean> existById(
            @PathVariable("cours-id") String coursId
    ) {
        return ResponseEntity.ok(service.existById(coursId));
    }

    @GetMapping("/{cours-id}")
    public ResponseEntity<CoursResponse> findById(
            @PathVariable("cours-id") String coursId
    ) {
        return ResponseEntity.ok(service.findById(coursId));
    }

    @DeleteMapping("/{cours-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("cours-id") String coursId
    ) {
        service.deleteCours(coursId);
        return ResponseEntity.accepted().build();
    }
}
