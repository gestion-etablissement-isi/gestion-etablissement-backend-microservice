package com.isi.institution.salle;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/salles")
@Slf4j
public class SalleController {

    private final SalleService service;

    @PostMapping
    public ResponseEntity<String> createSalle(
            @RequestBody @Valid SalleRequest request
    ) {
        log.info("Requête reçue pour créer une salle : {}", request);
        String salleId = service.createSalle(request);
        log.info("Salle créée avec succès avec l'ID : {}", salleId);
        return ResponseEntity.ok(salleId);
    }

    @GetMapping
    public ResponseEntity<List<SalleResponse>> findAll() {
        log.info("Requête reçue pour récupérer toutes les salles");
        List<SalleResponse> salles = service.findAllSalles();
        log.info("Retour de {} salles", salles.size());
        return ResponseEntity.ok(salles);
    }

    @GetMapping("/{salle-id}")
    public ResponseEntity<SalleResponse> findById(
            @PathVariable("salle-id") String salleId
    ) {
        log.info("Requête reçue pour récupérer la salle avec l'ID : {}", salleId);
        SalleResponse salle = service.findById(salleId);
        log.info("Retour de la salle avec l'ID : {}", salleId);
        return ResponseEntity.ok(salle);
    }

    @DeleteMapping("/{salle-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("salle-id") String salleId
    ) {
        log.info("Requête reçue pour supprimer la salle avec l'ID : {}", salleId);
        service.deleteSalle(salleId);
        log.info("Salle supprimée avec succès avec l'ID : {}", salleId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{salle-id}")
    public ResponseEntity<Void> updateSalle(
            @PathVariable("salle-id") String salleId,
            @RequestBody @Valid SalleRequest request
    ) {
        log.info("Requête reçue pour mettre à jour la salle avec l'ID : {}", salleId);
        request = new SalleRequest(salleId, request.nom());
        service.updateSalle(request);
        log.info("Salle mise à jour avec succès avec l'ID : {}", salleId);
        return ResponseEntity.accepted().build();
    }
}
