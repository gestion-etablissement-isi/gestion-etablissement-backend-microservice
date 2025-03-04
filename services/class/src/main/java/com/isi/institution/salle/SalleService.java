package com.isi.institution.salle;

import com.isi.institution.exception.ClasseNotFoundException;
import com.isi.institution.exception.SalleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalleService {

    private final SalleRepository repository;
    private final SalleMapper mapper;

    public String createSalle(SalleRequest request) {
        return repository.save(mapper.toSalle(request)).getId();
    }

    public List<SalleResponse> findAllSalles() {
        return repository.findAll().stream()
                .map(mapper::fromSalle)
                .collect(Collectors.toList());
    }

    public SalleResponse findById(String id) {
        return repository.findById(id)
                .map(mapper::fromSalle)
                .orElseThrow(() -> new SalleNotFoundException(
                        String.format("Aucune classe n'a été trouvée avec l'ID: %s", id)
                ));
    }

    public void updateSalle(SalleRequest request) {
        Salle salle = repository.findById(request.id())
                .orElseThrow(() -> new SalleNotFoundException(
                        String.format("Impossible de mettre à jour la salle : Aucune salle trouvée avec l'ID fourni : %s", request.id())
                ));
        salle.setNom(request.nom());
        repository.save(salle);
    }

    public void deleteSalle(String id) {
        repository.deleteById(id);
    }
}
