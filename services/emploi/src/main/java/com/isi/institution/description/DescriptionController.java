package com.isi.institution.description;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/description")
@RequiredArgsConstructor
public class DescriptionController {
    private final DescriptionService service;
    @PostMapping
    public ResponseEntity<String> createDescription(
            @RequestBody @Valid DescriptionRequest request
    ) {
        return ResponseEntity.ok(service.createDescription(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateDescription(
            @RequestBody @Valid DescriptionRequest request
    ) {
        service.updateDescription(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<DescriptionResponse>> findAll() {
        return ResponseEntity.ok(service.findAllDescription());
    }

    @GetMapping("/exist/{description-id}")
    public ResponseEntity<Boolean> existById(
            @PathVariable("description-id") String descriptionId
    ) {
        return ResponseEntity.ok(service.existById(descriptionId));
    }


    @GetMapping("/{description-id}")
    public ResponseEntity<DescriptionResponse> findById(
            @PathVariable("description-id") String descriptionId
    ) {
        return ResponseEntity.ok(service.findById(descriptionId));
    }

    @DeleteMapping("/{description-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("description-id") String descriptionId
    ) {
        service.deleteDescription(descriptionId);
        return ResponseEntity.accepted().build();
    }

}

