package com.isi.etablissement.matiere;

import com.isi.etablissement.cours.CoursRequest;
import com.isi.etablissement.cours.CoursResponse;
import com.isi.etablissement.cours.CoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matiere")
@RequiredArgsConstructor
public class MatiereController {
    private final MatiereService service;

    @PostMapping
    public ResponseEntity<String> createMatiere(
            @RequestBody @Valid MatiereRequest request
    ) {
        return ResponseEntity.ok(service.createMatiere(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateMatiere(
            @RequestBody @Valid MatiereRequest request
    ) {
        service.updateMatiere(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<MatiereResponse>> findAll() {
        return ResponseEntity.ok(service.findAllMatieres());
    }

    @GetMapping("/exist/{matiere-id}")
    public ResponseEntity<Boolean> existById(
            @PathVariable("matiere-id") String matiereId
    ) {
        return ResponseEntity.ok(service.existById(matiereId));
    }

    @GetMapping("/{matiere-id}")
    public ResponseEntity<MatiereResponse> findById(
            @PathVariable("matiere-id") String matiereId
    ) {
        return ResponseEntity.ok(service.findById(matiereId));
    }

    @DeleteMapping("/{matiere-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("matiere-id") String matiereId
    ) {
        service.deleteMatiere(matiereId);
        return ResponseEntity.accepted().build();
    }
}
