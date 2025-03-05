package com.isi.etablissement.professeur;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
    name = "professeur-service",
    url = "${application.config.professeur-url}"
)
public interface ProfesseurClient {
    @GetMapping("/{professeur-id}")
    Optional<ProfesseurResponse> findProfesseurById(
            @PathVariable("professeur-id") String professeurId
    );

}
