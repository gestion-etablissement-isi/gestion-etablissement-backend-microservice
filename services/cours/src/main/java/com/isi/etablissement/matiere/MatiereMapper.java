package com.isi.etablissement.matiere;

import com.isi.etablissement.cours.Cours;
import com.isi.etablissement.cours.CoursRequest;
import com.isi.etablissement.cours.CoursResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class MatiereMapper {
    public Matiere toMatiere(@Valid MatiereRequest request) {
        if(request == null) {
            return null;
        }
        return Matiere.builder()
                .id(request.id())
                .libelle(request.libelle())
                .statut(request.statut())
                .build();
    }

    public MatiereResponse fromMatiere(Matiere matiere) {
        return new MatiereResponse(
                matiere.getId(),
                matiere.getLibelle(),
                matiere.getStatut()
        );
    }
}
