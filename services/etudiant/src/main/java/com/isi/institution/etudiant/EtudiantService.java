package com.isi.institution.etudiant;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtudiantService {

    private final EtudiantRepository repository;
    private final EtudiantMapper mapper;

    public String createStudent(EtudiantRequest request) {
        Etudiant student = mapper.toStudentEntity(request);
        return repository.save(student).getId();
    }

    public List<EtudiantResponse> findAllStudents() {
        return repository.findAll().stream()
                .map(mapper::fromStudentEntity)
                .collect(Collectors.toList());
    }

    public EtudiantResponse findById(String id) {
        return repository.findById(id)
                .map(mapper::fromStudentEntity)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
    }

    public void updateStudent(String studentId, EtudiantRequest request) {
        Etudiant etudiant = repository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        mergeStudents(etudiant, request);
        repository.save(etudiant);
    }

    private void mergeStudents(Etudiant etudiant, EtudiantRequest request) {
        if (StringUtils.isNotBlank(request.nom())) {
            etudiant.setNom(request.nom());
        }
        if (StringUtils.isNotBlank(request.prenom())) {
            etudiant.setPrenom(request.prenom());
        }
        if (StringUtils.isNotBlank(request.email())) {
            etudiant.setEmail(request.email());
        }
        if (StringUtils.isNotBlank(request.tel())) {
            etudiant.setTel(request.tel());
        }
    }
    public void deleteStudent(String id) {
        repository.deleteById(id);
    }
}
