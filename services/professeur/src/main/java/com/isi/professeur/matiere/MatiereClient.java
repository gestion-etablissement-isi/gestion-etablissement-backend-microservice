package com.isi.professeur.matiere;

import com.isi.professeur.professeur.ProfesseurResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "cours-service",
        url = "${application.config.matiere-url}"
)
public interface MatiereClient {
    @GetMapping("/{matiere-id}")
    Optional<MatiereResponse> findMatiereById(
            @PathVariable("matiere-id") String matiereId
    );
}
