
package com.isi.institution.inscription;

import com.isi.institution.classe.ClasseClient;
import com.isi.institution.etudiant.Inscription;
import com.isi.institution.exception.ClasseNotFoundException;
import com.isi.institution.exception.EtudiantExistException;
import com.isi.institution.kafka.InscriptionConfirmation;
import com.isi.institution.kafka.InscriptionEtudiant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InscriptionService {
    private final InscriptionRepository repository;
    private final InscriptionMapper mapper;
    private final ClasseClient classeClient;
    private final InscriptionEtudiant inscriptionEtudiant;
    private final KafkaTemplate<String, InscriptionRequest> kafkaTemplate;

    @Transactional
    public InscriptionResponse inscrire(InscriptionRequest request) {
        try {

            classeClient.findClassById(request.classeId())
                    .orElseThrow(() -> new ClasseNotFoundException("Classe non trouvée"));


            if (repository.existsByEtudiantIdAndAnneeScolaire(request.etudiantId(), request.anneeScolaire())) {
                log.error("Inscription échouée : Étudiant {} déjà inscrit pour l'année {}",
                        request.etudiantId(), request.anneeScolaire());
                throw new EtudiantExistException("Étudiant déjà inscrit pour cette année scolaire");
            }

            // Créer et enregistrer l'inscription
            Inscription inscription = mapper.toInscription(request);
            inscription = repository.save(inscription);
            log.info("Inscription enregistrée avec succès. ID : {}", inscription.getId());

            // Publier un événement Kafka
            InscriptionConfirmation confirmation = new InscriptionConfirmation(
                    inscription.getId(),
                    request.etudiantId(),
                    request.classeId(),
                    request.anneeScolaire(),
                    LocalDateTime.now()
            );
            inscriptionEtudiant.sendInscriptionConfirmation(confirmation);
            log.info("Événement Kafka envoyé avec succès pour l'inscription : {}", confirmation);

            return mapper.fromInscription(inscription);
        } catch (Exception e) {
            log.error("Erreur lors de l'inscription", e);
            throw e;
        }
    }
    public List<InscriptionResponse> getAllInscriptions() {
        return repository.findAll().stream()
                .map(mapper::fromInscription)
                .collect(Collectors.toList());
    }

    public InscriptionResponse getInscriptionById(String id) {
        return repository.findById(id)
                .map(mapper::fromInscription)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
    }

    @Transactional
    public void deleteInscription(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Inscription non trouvée");
        }
        repository.deleteById(id);
    }
}