package com.isi.institution.creneau;

import com.isi.institution.cours.CoursClient;
import com.isi.institution.description.Description;
import com.isi.institution.description.DescriptionController;
import com.isi.institution.description.DescriptionRequest;
import com.isi.institution.exception.BusinessException;
import com.isi.institution.exception.CreneauNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CreneauService {
    private final CreneauRepository repository;
    private final CreneauMapper mapper;
    private final CoursClient coursClient;
    private final DescriptionController descriptionController;

    public String createCreneau(@Valid CreneauRequest request) {
        var cours = this.coursClient.findCoursById(request.coursId())
                .orElseThrow(() -> new BusinessException("Impossible de creer un creneau:: Aucun cours trouvé avec cet ID::"));
        if (request.descriptions() == null || request.descriptions().isEmpty()) {
            throw new BusinessException("La liste de descriptions ne peut pas être vide");
        }
        var creneau = repository.save(mapper.toCreneau(request));
        for (Description desc : request.descriptions()) {
            descriptionController.createDescription(new DescriptionRequest(
                    desc.getId(),
                    desc.getDateCours(),
                    desc.getHeureDebut(),
                    desc.getHeureFin(),
                    desc.getDescription()
            ));
        }

        return creneau.getId();
    }

    public String updateCreneau(@Valid CreneauRequest request) {
        var creneau = repository.findById(request.id())
                .orElseThrow(() -> new CreneauNotFoundException(
                        format("Impossible de modifier le créneau : Aucun créneau trouvé avec cet ID : %s", request.id())
                ));

        mergeCreneau(creneau, request);
        repository.save(creneau);
        return creneau.getId();
    }

    private void mergeCreneau(Creneau creneau, @Valid CreneauRequest request) {
        // Vérifier si la liste de descriptions est non vide
        if (request.descriptions() != null && !request.descriptions().isEmpty()) {
            creneau.setDescriptions(request.descriptions());
        }

        // Vérifier si le coursId n'est pas vide
        if (StringUtils.isNotBlank(request.coursId())) {
            creneau.setCoursId(request.coursId());
        }
    }

    public List<CreneauResponse> findAllCreneau() {
        return repository.findAll()
                .stream()
                .map(mapper::fromCreneau)
                .collect(Collectors.toList());
    }

    public Boolean existById(String creneauId) {
        return repository.findById(creneauId)
                .isPresent();
    }

    public CreneauResponse findById(String creneauId) {
        return repository.findById(creneauId)
                .map(mapper::fromCreneau)
                .orElseThrow(() -> new CreneauNotFoundException(
                        format("Impossible de trouver le creneau:: Aucun creneau trouvé avec cet ID:: %s", creneauId)
                ));
    }

    public void deleteCreneau(String creneauId) {
        repository.deleteById(creneauId);
    }
}
