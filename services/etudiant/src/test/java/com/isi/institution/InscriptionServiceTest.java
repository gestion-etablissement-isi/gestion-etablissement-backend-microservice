package com.isi.institution;

import com.isi.institution.classe.ClasseClient;
import com.isi.institution.classe.ClasseResponse;
import com.isi.institution.etudiant.Inscription;
import com.isi.institution.exception.ClasseNotFoundException;
import com.isi.institution.exception.EtudiantExistException;
import com.isi.institution.inscription.*;
import com.isi.institution.kafka.InscriptionConfirmation;
import com.isi.institution.kafka.InscriptionEtudiant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InscriptionServiceTest {

    @Mock
    private InscriptionRepository repository;

    @Mock
    private InscriptionMapper mapper;

    @Mock
    private ClasseClient classeClient;

    @Mock
    private InscriptionEtudiant inscriptionEtudiant;

    @Mock
    private KafkaTemplate<String, InscriptionRequest> kafkaTemplate;

    @InjectMocks
    private InscriptionService service;

    private Inscription inscription;
    private InscriptionRequest request;
    private InscriptionResponse response;

    private static final String ID = "inscription123";
    private static final String ETUDIANT_ID = "etudiant123";
    private static final String CLASSE_ID = "classe123";
    private static final String ANNEE_SCOLAIRE = "2024-2025";

    @BeforeEach
    void setUp() {
        inscription = new Inscription();
        inscription.setId(ID);
        inscription.setEtudiantId(ETUDIANT_ID);
        inscription.setClasseId(CLASSE_ID);
        inscription.setAnneeScolaire(ANNEE_SCOLAIRE);

        request = new InscriptionRequest(ETUDIANT_ID, CLASSE_ID, ANNEE_SCOLAIRE);
        response = new InscriptionResponse(ID, ETUDIANT_ID, CLASSE_ID, ANNEE_SCOLAIRE);
    }

    @Test
    void testInscrire_Success() {
        // Simuler une classe existante
        ClasseResponse mockedClasse = new ClasseResponse(CLASSE_ID, "Math", ANNEE_SCOLAIRE);
        when(classeClient.findClassById(CLASSE_ID)).thenReturn(Optional.of(mockedClasse));

        when(repository.existsByEtudiantIdAndAnneeScolaire(ETUDIANT_ID, ANNEE_SCOLAIRE)).thenReturn(false);
        when(mapper.toInscription(request)).thenReturn(inscription);
        when(repository.save(inscription)).thenReturn(inscription);
        when(mapper.fromInscription(inscription)).thenReturn(response);

        // Exécution
        InscriptionResponse result = service.inscrire(request);

        // Vérifications
        assertEquals(response, result);
        verify(repository, times(1)).save(inscription);
        verify(inscriptionEtudiant, times(1)).sendInscriptionConfirmation(any(InscriptionConfirmation.class));
    }

    @Test
    void testInscrire_Fail_ClasseNotFound() {
        // Simuler l'absence de la classe
        when(classeClient.findClassById(CLASSE_ID)).thenReturn(Optional.empty());

        // Exécution & Vérification
        assertThrows(ClasseNotFoundException.class, () -> service.inscrire(request));
        verify(repository, never()).save(any());
    }

    @Test
    void testInscrire_Fail_EtudiantAlreadyInscrit() {
        // Simuler une classe existante
        ClasseResponse mockedClasse = new ClasseResponse(CLASSE_ID, "Math", ANNEE_SCOLAIRE);
        when(classeClient.findClassById(CLASSE_ID)).thenReturn(Optional.of(mockedClasse));

        // Simuler l'existence d'une inscription pour l'étudiant
        when(repository.existsByEtudiantIdAndAnneeScolaire(ETUDIANT_ID, ANNEE_SCOLAIRE)).thenReturn(true);

        // Exécution & Vérification
        assertThrows(EtudiantExistException.class, () -> service.inscrire(request));
        verify(repository, never()).save(any());
    }

    @Test
    void testGetAllInscriptions() {
        // Préparation
        List<Inscription> inscriptions = List.of(inscription);
        when(repository.findAll()).thenReturn(inscriptions);
        when(mapper.fromInscription(inscription)).thenReturn(response);

        // Exécution
        List<InscriptionResponse> result = service.getAllInscriptions();

        // Vérifications
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).fromInscription(inscription);
    }

    @Test
    void testGetInscriptionById_Success() {
        // Préparation
        when(repository.findById(ID)).thenReturn(Optional.of(inscription));
        when(mapper.fromInscription(inscription)).thenReturn(response);

        // Exécution
        InscriptionResponse result = service.getInscriptionById(ID);

        // Vérifications
        assertEquals(response, result);
        verify(repository, times(1)).findById(ID);
    }

    @Test
    void testGetInscriptionById_NotFound() {
        // Préparation
        when(repository.findById(ID)).thenReturn(Optional.empty());

        // Exécution & Vérification
        assertThrows(RuntimeException.class, () -> service.getInscriptionById(ID));
        verify(repository, times(1)).findById(ID);
    }

    @Test
    void testDeleteInscription_Success() {
        // Préparation
        when(repository.existsById(ID)).thenReturn(true);
        doNothing().when(repository).deleteById(ID);

        // Exécution
        service.deleteInscription(ID);

        // Vérifications
        verify(repository, times(1)).deleteById(ID);
    }

    @Test
    void testDeleteInscription_NotFound() {
        // Préparation
        when(repository.existsById(ID)).thenReturn(false);

        // Exécution & Vérification
        assertThrows(RuntimeException.class, () -> service.deleteInscription(ID));
        verify(repository, never()).deleteById(any());
    }
}
