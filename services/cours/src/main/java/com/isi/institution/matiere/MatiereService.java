package com.isi.institution.matiere;

import com.isi.institution.exception.MatiereNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class MatiereService {
    private final MatiereRepository repository;
    private final MatiereMapper mapper;
    public String createMatiere(@Valid MatiereRequest request) {
        var matiere = repository.save(mapper.toMatiere(request));
        return matiere.getId();
    }

    public String updateMatiere(@Valid MatiereRequest request) {
        var matiere = repository.findById(request.id())
                .orElseThrow(() -> new MatiereNotFoundException(
                        format("Impossible de modifier la matiere:: Aucune matiere trouvé avec cet ID:: %s", request.id())
                ));
        mergerMatiere(matiere, request);
        repository.save(matiere);
        return matiere.getId();
    }

    private void mergerMatiere(Matiere matiere, @Valid MatiereRequest request) {
        if(StringUtils.isNotBlank((request.libelle()))) {
            matiere.setLibelle(request.libelle());
        }
        if(StringUtils.isNotBlank((request.statut()))) {
            matiere.setStatut(request.statut());
        }

    }

    public List<MatiereResponse> findAllMatieres() {
        return repository.findAll()
                .stream()
                .map(mapper::fromMatiere)
                .collect(Collectors.toList());
    }

    public Boolean existById(String matiereId) {
        return repository.findById(matiereId)
                .isPresent();
    }

    public MatiereResponse findById(String matiereId) {
        return repository.findById(matiereId)
                .map(mapper::fromMatiere)
                .orElseThrow(() -> new MatiereNotFoundException(
                        format("Impossible de modifier la matiere:: Aucune matiere trouvé avec cet ID:: %s", matiereId)
                ));
    }

    public void deleteMatiere(String matiereId) {
        repository.deleteById(matiereId);
    }
}
