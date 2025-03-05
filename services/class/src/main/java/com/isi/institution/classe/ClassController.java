package com.isi.institution.classe;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/class")
@Slf4j
public class ClassController {

    private final ClasseService service;

    @PostMapping
    public ResponseEntity<String> createClass(
            @RequestBody @Valid ClasseRequest request
    ) {
        log.info("Requête reçue pour créer la classe : {}", request);
        String classId = service.createClass(request);
        log.info("Classe créée avec succès avec l'ID : {}", classId);
        return ResponseEntity.ok(classId);
    }

    @GetMapping
    public ResponseEntity<List<ClasseResponse>> findAll() {
        log.info("Requête reçue pour récupérer toutes les classes");
        List<ClasseResponse> classes = service.findAllClass();
        log.info("Retour de {} classes", classes.size());
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{class-id}")
    public ResponseEntity<ClasseResponse> findById(
            @PathVariable("class-id") String classId
    ) {
        log.info("Requête reçue pour récupérer la classe avec l'ID : {}", classId);
        ClasseResponse classe = service.findById(classId);
        log.info("Retour de la classe avec l'ID : {}", classId);
        return ResponseEntity.ok(classe);
    }

    @DeleteMapping("/{class-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("class-id") String classId
    ) {
        log.info("Requête reçue pour supprimer la classe avec l'ID : {}", classId);
        service.deleteClass(classId);
        log.info("Classe supprimée avec succès avec l'ID : {}", classId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{class-id}")
    public ResponseEntity<Void> updateClass(
            @PathVariable("class-id") String classId,
            @RequestBody @Valid ClasseRequest request
    ) {
        log.info("Requête reçue pour mettre à jour la classe avec l'ID : {}", classId);
        // Remplacer request.id() par classId ici, si nécessaire
        request = new ClasseRequest(classId, request.nom(), request.annee_scolaire());
        service.updateClass(request);
        log.info("Classe mise à jour avec succès avec l'ID : {}", classId);
        return ResponseEntity.accepted().build();
    }

}
