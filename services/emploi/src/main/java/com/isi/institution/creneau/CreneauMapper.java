package com.isi.institution.creneau;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CreneauMapper {
    public Creneau toCreneau(@Valid CreneauRequest request) {
        if(request == null) {
            return null;
        }
        return Creneau.builder()
                .id(request.id())
                .coursId(request.coursId())
                .descriptions(request.descriptions())
                .build();
    }

    public CreneauResponse fromCreneau(Creneau creneau) {
        return new CreneauResponse(
                creneau.getId(),
                creneau.getCoursId(),
                creneau.getDescriptions()
        );
    }
}
