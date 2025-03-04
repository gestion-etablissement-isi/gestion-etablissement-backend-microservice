package com.isi.institution.etudiant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/etudiants")
@Slf4j
public class EtudiantController {

    private final EtudiantService service;

    @PostMapping
    public ResponseEntity<String> createStudent(
            @RequestBody @Valid EtudiantRequest request
    ) {
        log.info("Requête reçue pour créer un étudiant : {}", request);
        String etudiantId = service.createStudent(request);
        log.info("Étudiant créé avec succès avec l'ID : {}", etudiantId);
        return ResponseEntity.ok(etudiantId);
    }

    @GetMapping
    public ResponseEntity<List<EtudiantResponse>> findAll() {
        log.info("Requête reçue pour récupérer tous les étudiants");
        List<EtudiantResponse> etudiants = service.findAllStudents();
        log.info("Retour de {} étudiants", etudiants.size());
        return ResponseEntity.ok(etudiants);
    }

    @GetMapping("/{etudiant-id}")
    public ResponseEntity<EtudiantResponse> findById(
            @PathVariable("etudiant-id") String etudiantId
    ) {
        log.info("Requête reçue pour récupérer l'étudiant avec l'ID : {}", etudiantId);
        EtudiantResponse student = service.findById(etudiantId);
        log.info("Retour de l'étudiant avec l'ID : {}", etudiantId);
        return ResponseEntity.ok(student);
    }
    @PutMapping("/{etudiant-id}")
    public ResponseEntity<Void> updateStudent(
            @PathVariable("etudiant-id") String etudiantId,
            @RequestBody @Valid EtudiantRequest request
    ) {
        log.info("Requête reçue pour mettre à jour l'étudiant avec l'ID : {}", etudiantId);
        service.updateStudent(etudiantId, request);
        log.info("Étudiant mis à jour avec succès avec l'ID : {}", etudiantId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{etudiant-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("etudiant-id") String etudiantId
    ) {
        log.info("Requête reçue pour supprimer l'étudiant avec l'ID : {}", etudiantId);
        service.deleteStudent(etudiantId);
        log.info("Étudiant supprimé avec succès avec l'ID : {}", etudiantId);
        return ResponseEntity.accepted().build();
    }
}
