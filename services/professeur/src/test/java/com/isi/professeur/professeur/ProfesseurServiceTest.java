package com.isi.professeur.professeur;

import com.isi.professeur.exception.BusinessException;
import com.isi.professeur.exception.ProfesseurNotFoundException;
import com.isi.professeur.matiere.MatiereClient;
import com.isi.professeur.matiere.MatiereResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfesseurServiceTest {

    @Mock
    private ProfesseurRepository repository;

    @Mock
    private ProfesseurMapper mapper;

    @Mock
    private MatiereClient matiereClient;

    @InjectMocks
    private ProfesseurService service;

    @Test
    void testCreateProfesseur() {
        // Données de test
        ProfesseurRequest request = new ProfesseurRequest(
                "1", "Dupont", "Jean", "jean.dupont@example.com", "Actif", "matiere1"
        );

        // Simuler la réponse de MatiereClient
        when(matiereClient.findMatiereById("matiere1")).thenReturn(Optional.of(new MatiereResponse("matiere1", "Mathématiques", "Actif")));

        // Simuler le mapper et le repository
        when(mapper.toProfesseur(request)).thenReturn(new Professeur("1", "Dupont", "Jean", "jean.dupont@example.com", "Actif", "matiere1"));
        when(repository.save(any(Professeur.class))).thenReturn(new Professeur("1", "Dupont", "Jean", "jean.dupont@example.com", "Actif", "matiere1"));

        // Appeler la méthode à tester
        String result = service.createProfesseur(request);

        // Vérifications
        assertNotNull(result);
        assertEquals("1", result);
        verify(repository, times(1)).save(any(Professeur.class));
    }

    @Test
    void testCreateProfesseurWithInvalidMatiere() {
        // Données de test
        ProfesseurRequest request = new ProfesseurRequest(
                "1", "Dupont", "Jean", "jean.dupont@example.com", "Actif", "matiere1"
        );

        // Simuler une matière non trouvée
        when(matiereClient.findMatiereById("matiere1")).thenReturn(Optional.empty());

        // Vérifier que l'exception est levée
        assertThrows(BusinessException.class, () -> service.createProfesseur(request));
    }

    @Test
    void testUpdateProfesseur() {
        // Données de test
        ProfesseurRequest request = new ProfesseurRequest(
                "1", "Dupont", "Jean", "jean.dupont@example.com", "Actif", "matiere1"
        );

        // Simuler un professeur existant
        Professeur professeur = new Professeur("1", "Dupont", "Jean", "jean.dupont@example.com", "Actif", "matiere1");
        when(repository.findById("1")).thenReturn(Optional.of(professeur));

        // Simuler la réponse de MatiereClient
        when(matiereClient.findMatiereById("matiere1")).thenReturn(Optional.of(new MatiereResponse("matiere1", "Mathématiques", "Actif")));

        // Simuler le repository
        when(repository.save(any(Professeur.class))).thenReturn(professeur);

        // Appeler la méthode à tester
        String result = service.updateProfesseur(request);

        // Vérifications
        assertNotNull(result);
        assertEquals("1", result);
        verify(repository, times(1)).save(any(Professeur.class));
    }

    @Test
    void testUpdateProfesseurWithInvalidId() {
        // Données de test
        ProfesseurRequest request = new ProfesseurRequest(
                "1", "Dupont", "Jean", "jean.dupont@example.com", "Actif", "matiere1"
        );

        // Simuler un professeur non trouvé
        when(repository.findById("1")).thenReturn(Optional.empty());

        // Vérifier que l'exception est levée
        assertThrows(ProfesseurNotFoundException.class, () -> service.updateProfesseur(request));
    }

    @Test
    void testFindAllProfesseurs() {
        // Simuler la réponse du repository
        when(repository.findAll()).thenReturn(List.of(
                new Professeur("1", "Dupont", "Jean", "jean.dupont@example.com", "Actif", "matiere1"),
                new Professeur("2", "Martin", "Sophie", "sophie.martin@example.com", "Inactif", "matiere2")
        ));

        // Simuler la réponse du mapper
        when(mapper.fromProfesseur(any(Professeur.class))).thenAnswer(invocation -> {
            Professeur professeur = invocation.getArgument(0);
            return new ProfesseurResponse(
                    professeur.getId(),
                    professeur.getNom(),
                    professeur.getPrenom(),
                    professeur.getEmail(),
                    professeur.getStatut(),
                    professeur.getMatiereId()
            );
        });

        // Appeler la méthode à tester
        List<ProfesseurResponse> result = service.findAllProfesseurs();

        // Vérifications
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testExistById() {
        // Simuler un professeur existant
        when(repository.findById("1")).thenReturn(Optional.of(new Professeur("1", "Dupont", "Jean", "jean.dupont@example.com", "Actif", "matiere1")));

        // Appeler la méthode à tester
        Boolean result = service.existById("1");

        // Vérifications
        assertTrue(result);
    }

    @Test
    void testFindById() {
        // Simuler un professeur existant
        Professeur professeur = new Professeur("1", "Dupont", "Jean", "jean.dupont@example.com", "Actif", "matiere1");
        when(repository.findById("1")).thenReturn(Optional.of(professeur));

        // Simuler la réponse du mapper
        when(mapper.fromProfesseur(professeur)).thenReturn(new ProfesseurResponse("1", "Dupont", "Jean", "jean.dupont@example.com", "Actif", "matiere1"));

        // Appeler la méthode à tester
        ProfesseurResponse result = service.findById("1");

        // Vérifications
        assertNotNull(result);
        assertEquals("1", result.id());
        assertEquals("Dupont", result.nom());
        assertEquals("Jean", result.prenom());
        assertEquals("jean.dupont@example.com", result.email());
        assertEquals("Actif", result.statut());
        assertEquals("matiere1", result.matiereId());
    }

    @Test
    void testFindByIdWithInvalidId() {
        // Simuler un professeur non trouvé
        when(repository.findById("1")).thenReturn(Optional.empty());

        // Vérifier que l'exception est levée
        assertThrows(ProfesseurNotFoundException.class, () -> service.findById("1"));
    }

    @Test
    void testDeleteProfesseur() {
        // Simuler la suppression
        doNothing().when(repository).deleteById("1");

        // Appeler la méthode à tester
        service.deleteProfesseur("1");

        // Vérifications
        verify(repository, times(1)).deleteById("1");
    }
}
