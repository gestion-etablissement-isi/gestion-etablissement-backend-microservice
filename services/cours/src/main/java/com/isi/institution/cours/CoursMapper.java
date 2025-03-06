package com.isi.institution.cours;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CoursMapper {
    public Cours toCours(@Valid CoursRequest request) {
        if(request == null) {
            return null;
        }
        return Cours.builder()
                .id(request.id())
                .titre(request.titre())
                .volumeHoraire(request.volumeHoraire())
                .coefficient(request.coefficient())
                .anneeAcademique(request.anneeAcademique())
                .classeId(request.classeId())
                .professeurId(request.professeurId())
                .matiereId(request.matiereId())
                .build();
    }

    public CoursResponse fromCours(Cours cours) {
        return new CoursResponse(
                cours.getId(),
                cours.getTitre(),
                cours.getVolumeHoraire(),
                cours.getCoefficient(),
                cours.getAnneeAcademique(),
                cours.getClasseId(),
                cours.getProfesseurId(),
                cours.getMatiereId()
        );
    }
}
