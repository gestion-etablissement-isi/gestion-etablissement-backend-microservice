package com.isi.institution.description;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class DescriptionMapper {
    public Description toDescription(@Valid DescriptionRequest request) {
        if(request == null) {
            return null;
        }
        return Description.builder()
                .id(request.id())
                .dateCours(request.dateCours())
                .heureDebut(request.heureDebut())
                .heureFin(request.heureFin())
                .description(request.description())
                .build();
    }

    public DescriptionResponse fromDescription(Description dc) {
        return new DescriptionResponse(
                dc.getId(),
                dc.getDateCours(),
                dc.getHeureDebut(),
                dc.getHeureFin(),
                dc.getDescription()
        );
    }
}
