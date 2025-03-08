package com.isi.institution.description;

import com.isi.institution.exception.DescriptionNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class DescriptionService {
    private final DescriptionMapper mapper;
    private final DescriptionRepository repository;
    public String createDescription(@Valid DescriptionRequest request) {

        var dc= repository.save(mapper.toDescription(request));
        return dc.getId();
    }
    public String updateDescription(@Valid DescriptionRequest request) {
        var dc = repository.findById(request.id())
                .orElseThrow(() -> new DescriptionNotFoundException(
                        format("Impossible de modifier la description du :: Aucun description trouvé avec cet ID:: %s", request.id())
                ));
        mergerDescription(dc, request);
        repository.save(dc);
        return dc.getId();
    }

    private void mergerDescription(Description dc, @Valid DescriptionRequest request) {
        if(StringUtils.isNotBlank(request.dateCours().toString())) {
            dc.setDateCours(request.dateCours());
        }
        if (StringUtils.isNotBlank(request.heureDebut())) {
            dc.setHeureDebut(request.heureDebut());
        }
        if (StringUtils.isNotBlank(request.heureFin())) {
            dc.setHeureFin(request.heureFin());
        }
        if(StringUtils.isNotBlank((request.description()))) {
            dc.setDescription(request.description());
        }


    }

    public List<DescriptionResponse> findAllDescription() {
        return repository.findAll()
                .stream()
                .map(mapper::fromDescription)
                .collect(Collectors.toList());
    }

    public Boolean existById(String descriptionId) {
        return repository.findById(descriptionId)
                .isPresent();
    }

    public DescriptionResponse findById(String descriptionId) {
        return repository.findById(descriptionId)
                .map(mapper::fromDescription)
                .orElseThrow(() -> new DescriptionNotFoundException(
                        format("Impossible de trouver la description du :: Aucun description trouvé avec cet ID:: %s", descriptionId)
                ));
    }

    public void deleteDescription(String descriptionId) {
        repository.deleteById(descriptionId);
    }
}
