package com.isi.institution.cours;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "cours-service",
        url = "${application.config.cours-url}"
)
public interface CoursClient {
    @GetMapping("/{cours-id}")
    Optional<CoursResponse> findCoursById(
            @PathVariable("cours-id") String coursId
    );
}
