package com.isi.etablissement.classe;

import com.isi.etablissement.professeur.ProfesseurResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "class-service",
        url = "${application.config.classe-url}"
)
public interface ClasseClient {
    @GetMapping("/{class-id}")
    Optional<ClasseResponse> findClasseById(
            @PathVariable("class-id") String classId
    );
}
