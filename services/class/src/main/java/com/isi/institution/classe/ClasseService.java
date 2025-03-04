package com.isi.institution.classe;



import com.isi.institution.exception.ClasseNotFoundException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ClasseService {

    private final ClasseRepository repository;
    private final ClasseMapper mapper;

    public String createClass(ClasseRequest request) {
        // Pas de conversion d'ID nécessaire ici
        return this.repository.save(mapper.toClasseEntity(request)).getId();
    }

    public List<ClasseResponse> findAllClass() {
        return this.repository.findAll().stream()
                .map(mapper::fromClasseEntity)
                .collect(Collectors.toList());
    }

    public ClasseResponse findById(String id) {
        return this.repository.findById(id)
                .map(mapper::fromClasseEntity)
                .orElseThrow(() -> new ClasseNotFoundException(
                        String.format("Aucune classe n'a été trouvée avec l'ID: %s", id)
                ));
    }

    public void updateClass(ClasseRequest request) {
        var classe = this.repository.findById(request.id())
                .orElseThrow(() -> new ClasseNotFoundException(
                        String.format("Impossible de mettre à jour la classe : Aucune classe trouvée avec l'ID fourni : %s", request.id())
                ));
        mergeClasse(classe, request);
        this.repository.save(classe);
    }

    private void mergeClasse(ClasseEntity classe, ClasseRequest request) {
        if (StringUtils.isNotBlank(request.nom())) {
            classe.setNom(request.nom());
        }
        if (StringUtils.isNotBlank(request.annee_scolaire())) {
            classe.setAnnee_scolaire(request.annee_scolaire());
        }
    }

    public void deleteClass(String id){
        repository.deleteById(id);
    }

}
