package com.isi.professeur.professeur;

import com.isi.professeur.exception.BusinessException;
import com.isi.professeur.exception.ProfesseurNotFoundException;
import com.isi.professeur.matiere.MatiereClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ProfesseurService {
    private final ProfesseurRepository repository;
    private final ProfesseurMapper mapper;
    private final MatiereClient matiereClient;
    public String createProfesseur(@Valid ProfesseurRequest request) {
        var matiere = this.matiereClient.findMatiereById(request.matiereId())
                .orElseThrow(() -> new BusinessException("Impossible de creer un cours:: Aucun professeur trouvé avec cet ID::"));
        var professeur = repository.save(mapper.toProfesseur(request));
        return professeur.getId();
    }

    public String updateProfesseur(@Valid ProfesseurRequest request) {
        var matiere = this.matiereClient.findMatiereById(request.matiereId())
                .orElseThrow(() -> new BusinessException("Impossible de creer un cours:: Aucun professeur trouvé avec cet ID::"));
        var professeur = repository.findById(request.id())
                .orElseThrow(() -> new ProfesseurNotFoundException(
                        format("Impossible de modifier le professeur:: Aucun professeur trouvé avec cet ID:: %s", request.id())
                ));
        mergerProfesseur(professeur, request);
        repository.save(professeur);
        return professeur.getId();
    }

    private void mergerProfesseur(Professeur prof, @Valid ProfesseurRequest request) {
        if(StringUtils.isNotBlank((request.nom()))) {
            prof.setNom(request.nom());
        }
        if(StringUtils.isNotBlank((request.prenom()))) {
            prof.setPrenom(request.prenom());
        }
        if(StringUtils.isNotBlank((request.email()))) {
            prof.setEmail(request.email());
        }
        if(StringUtils.isNotBlank((request.statut()))) {
            prof.setStatut(request.statut());
        }

    }

    public List<ProfesseurResponse> findAllProfesseurs() {
        return repository.findAll()
                .stream()
                .map(mapper::fromProfesseur)
                .collect(Collectors.toList());
    }

    public Boolean existById(String professeurId) {
        return repository.findById(professeurId)
                .isPresent();
    }

    public ProfesseurResponse findById(String professeurId) {
        return repository.findById(professeurId)
                .map(mapper::fromProfesseur)
                .orElseThrow(() -> new ProfesseurNotFoundException(
                        format("Impossible de trouver le professeur:: Aucun professeur trouvé avec cet ID:: %s", professeurId)
                ));
    }

    public void deleteProfesseur(String professeurId) {
        repository.deleteById(professeurId);
    }
}

