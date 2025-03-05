
package com.isi.institution.inscription;

import com.isi.institution.classe.ClasseClient;
import com.isi.institution.etudiant.Inscription;
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
            // Vérifier si la classe existe
            classeClient.findClassById(request.classeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classe non trouvée"));

            // Vérifier si l'étudiant est déjà inscrit pour cette année scolaire
            if (repository.existsByEtudiantIdAndAnneeScolaire(request.etudiantId(), request.anneeScolaire())) {
                log.error("Inscription échouée : Étudiant {} déjà inscrit pour l'année {}",
                        request.etudiantId(), request.anneeScolaire());
                throw new RuntimeException("Étudiant déjà inscrit pour cette année scolaire");
            }

            // Créer l'inscription
            Inscription inscription = mapper.toInscription(request);

            // Log avant la sauvegarde
            log.info("Tentative d'enregistrement de l'inscription : {}", inscription);

            inscription = repository.save(inscription);

            // Log après la sauvegarde
            log.info("Inscription enregistrée avec succès. ID : {}", inscription.getId());

            // Publier un événement Kafka pour la notification
            InscriptionConfirmation confirmation = new InscriptionConfirmation(
                    inscription.getId(),
                    request.etudiantId(),
                    request.classeId(),
                    request.anneeScolaire(),
                    LocalDateTime.now(),
                    true
            );

            try {
                inscriptionEtudiant.sendInscriptionConfirmation(confirmation);
                log.info("Événement Kafka envoyé avec succès pour l'inscription : {}", confirmation);
            } catch (Exception e) {
                log.error("Échec de l'envoi de l'événement Kafka", e);
                // Vous pouvez choisir de lancer une exception ou de gérer différemment
            }

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