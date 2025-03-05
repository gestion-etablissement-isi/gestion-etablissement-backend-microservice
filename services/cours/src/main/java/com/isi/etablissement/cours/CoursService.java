package com.isi.etablissement.cours;

import com.isi.etablissement.classe.ClasseClient;
import com.isi.etablissement.exception.BusinessException;
import com.isi.etablissement.exception.CoursNotFoundException;
import com.isi.etablissement.matiere.Matiere;
import com.isi.etablissement.matiere.MatiereController;
import com.isi.etablissement.matiere.MatiereResponse;
import com.isi.etablissement.professeur.ProfesseurClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CoursService {
    private final CoursRepository repository;
    private final CoursMapper mapper;
    private final ProfesseurClient professeurClient;
    private final ClasseClient classeClient;
    private final MatiereController matiereController;

    public String createCours(@Valid CoursRequest request) {
        var professeur = this.professeurClient.findProfesseurById(request.professeurId())
                                .orElseThrow(() -> new BusinessException("Impossible de creer un cours:: Aucun professeur trouvé avec cet ID::"));

        var classe = this.classeClient.findClasseById(request.classeId())
                .orElseThrow(() -> new BusinessException("Impossible de creer un cours:: Aucune classe trouvée avec cet ID::"));

        var cours = repository.save(mapper.toCours(request));
        return cours.getId();
    }

    public String updateCours(@Valid CoursRequest request) {
        var cours = repository.findById(request.id())
               .orElseThrow(() -> new CoursNotFoundException(
                        format("Impossible de modifier le cours:: Aucun cours trouvé avec cet ID:: %s", request.id())
                       ));
        var professeur = this.professeurClient.findProfesseurById(request.professeurId())
                .orElseThrow(() -> new BusinessException("Impossible de creer un cours:: Aucun professeur trouvé avec cet ID::"));
        var classe = this.classeClient.findClasseById(request.classeId())
                .orElseThrow(() -> new BusinessException("Impossible de creer un cours:: Aucune classe trouvée avec cet ID::"));
        mergerCours(cours, request);
        repository.save(cours);
        return cours.getId();
    }

    private void mergerCours(Cours cours, @Valid CoursRequest request) {
        if(StringUtils.isNotBlank((request.titre()))) {
            cours.setTitre(request.titre());
        }
        if (request.volumeHoraire() > 0) {
            cours.setVolumeHoraire(request.volumeHoraire());
        }
        if (request.coefficient() > 0) {
            cours.setCoefficient(request.coefficient());
        }
        if(StringUtils.isNotBlank((request.anneeAcademique()))) {
            cours.setAnneeAcademique(request.anneeAcademique());
        }
        if(StringUtils.isNotBlank((request.classeId()))) {
            cours.setClasseId(request.classeId());
        }
        if(StringUtils.isNotBlank((request.professeurId()))) {
            cours.setProfesseurId(request.professeurId());
        }
        if(StringUtils.isNotBlank(request.matiereId())){
            cours.setMatiereId(request.matiereId());
        }

    }

    public List<CoursResponse> findAllCours() {
        return repository.findAll()
                .stream()
                .map(mapper::fromCours)
                .collect(Collectors.toList());
    }

    public Boolean existById(String coursId) {
        return repository.findById(coursId)
                .isPresent();
    }

    public CoursResponse findById(String coursId) {
        return repository.findById(coursId)
               .map(mapper::fromCours)
               .orElseThrow(() -> new CoursNotFoundException(
                        format("Impossible de trouver le cours:: Aucun cours trouvé avec cet ID:: %s", coursId)
                ));
    }

    public void deleteCours(String coursId) {
        repository.deleteById(coursId);
    }
}
