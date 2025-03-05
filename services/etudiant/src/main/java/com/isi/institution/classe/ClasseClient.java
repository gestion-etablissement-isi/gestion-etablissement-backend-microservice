package com.isi.institution.classe;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "CLASS-SERVICE", url = "${spring.application.config.classe-url}")

public interface ClasseClient {
    @GetMapping("/{classe-id}")
    Optional<ClasseResponse> findClassById(@PathVariable("classe-id") String classeId);

}
