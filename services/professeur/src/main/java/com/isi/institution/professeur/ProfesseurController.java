package com.isi.institution.professeur;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/professeurs")
@Slf4j
public class ProfesseurController {
    private final ProfesseurService service;

    @PostMapping
    public ResponseEntity<Long> createProfesseur(
            @RequestBody @Valid ProfesseurRequest request
    ) {
        log.info("Requête reçue pour créer un Professeur : {}", request);
        Long professeurId = service.createProfesseur(request);
        log.info("Professeur créé avec succès avec l'ID : {}", professeurId);
        return ResponseEntity.ok(professeurId);
    }

    @GetMapping
    public ResponseEntity<List<ProfesseurResponse>> findAll() {
        log.info("Requête reçue pour récupérer tous les Professeur");
        List<ProfesseurResponse> Professeur = service.findAllProfesseur();
        log.info("Retour de {} Professeur", Professeur.size());
        return ResponseEntity.ok(Professeur);
    }

    @GetMapping("/{professeur-id}")
    public ResponseEntity<ProfesseurResponse> findById(
            @PathVariable("professeur-id") Long professeurId
    ) {
        log.info("Requête reçue pour récupérer le Professeur avec l'ID : {}", professeurId);
        ProfesseurResponse Professeur = service.findById(professeurId);
        log.info("Retour de le Professeur avec l'ID : {}", professeurId);
        return ResponseEntity.ok(Professeur);
    }
    @PutMapping("/{professeur-id}")
    public ResponseEntity<Void> updateProfesseur(
            @PathVariable("professeur-id") Long professeurId,
            @RequestBody @Valid ProfesseurRequest request
    ) {
        log.info("Requête reçue pour mettre à jour le Professeur avec l'ID : {}", professeurId);
        service.updateProfesseur(professeurId, request);
        log.info("Professeur mis à jour avec succès avec l'ID : {}", professeurId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{professeur-id}")
    public ResponseEntity<Void> deleteProfesseur(
            @PathVariable("professeur-id") Long professeurId
    ) {
        log.info("Requête reçue pour supprimer l'étudiant avec l'ID : {}", professeurId);
        service.deleteProfesseur(professeurId);
        log.info("Étudiant supprimé avec succès avec l'ID : {}", professeurId);
        return ResponseEntity.accepted().build();
    }
}
