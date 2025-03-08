package com.isi.professeur.professeur;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class ProfesseurMapper {
    public Professeur toProfesseur(@Valid ProfesseurRequest request) {
        if(request == null) {
            return null;
        }
        return Professeur.builder()
                .id(request.id())
                .nom(request.nom())
                .prenom(request.prenom())
                .email(request.email())
                .statut(request.statut())
                .build();
    }

    public ProfesseurResponse fromProfesseur(Professeur prof) {
        return new ProfesseurResponse(
                prof.getId(),
                prof.getNom(),
                prof.getPrenom(),
                prof.getEmail(),
                prof.getStatut(),
                prof.getMatiereId()
        );
    }
}

