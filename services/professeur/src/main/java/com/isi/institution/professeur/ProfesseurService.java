package com.isi.institution.professeur;

import com.isi.institution.exception.ProfesseurNotFoundException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfesseurService {
    private final ProfesseurRepository repository;
    private final ProfesseurMapper mapper;

    public Long createProfesseur(ProfesseurRequest request) {
        Professeur professeur = mapper.toProf(request);
        return repository.save(professeur).getId();
    }

    public List<ProfesseurResponse> findAllProfesseur() {
        return repository.findAll().stream()
                .map(mapper::fromProf)
                .collect(Collectors.toList());
    }

    public ProfesseurResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::fromProf)
                .orElseThrow(() -> new ProfesseurNotFoundException(String.format(" Aucun Professeur trouvée avec l'ID fourni : %s",id)));
    }

    public void updateProfesseur(Long id, ProfesseurRequest request) {
        Professeur professeur = repository.findById(id)
                .orElseThrow(() -> new ProfesseurNotFoundException(String.format("Impossible de mettre à jour le Professeur : Aucun Professeur trouvée avec l'ID fourni : %s", request.id())));
        mergeStudents(professeur, request);
        repository.save(professeur);
    }

    private void mergeStudents(Professeur professeur, ProfesseurRequest request) {
        if (StringUtils.isNotBlank(request.nom())) {
            professeur.setNom(request.nom());
        }
        if (StringUtils.isNotBlank(request.prenom())) {
            professeur.setPrenom(request.prenom());
        }
        if (StringUtils.isNotBlank(request.email())) {
            professeur.setEmail(request.email());
        }
        if (StringUtils.isNotBlank(request.tel())) {
            professeur.setTel(request.tel());
        }
        if (StringUtils.isNotBlank(request.specialite())) {
            professeur.setTel(request.specialite());
        }
    }
    public void deleteProfesseur(Long id) {
        repository.deleteById(id);
    }
}
