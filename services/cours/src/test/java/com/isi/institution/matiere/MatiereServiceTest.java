package com.isi.institution.matiere;

import com.isi.institution.exception.MatiereNotFoundException;
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
class MatiereServiceTest {

    @Mock
    private MatiereRepository repository;

    @Mock
    private MatiereMapper mapper;

    @InjectMocks
    private MatiereService service;

    @Test
    void testCreateMatiere() {
        // Données de test
        MatiereRequest request = new MatiereRequest("1", "Mathématiques", "Actif");

        // Simuler le mapper et le repository
        when(mapper.toMatiere(request)).thenReturn(new Matiere("1", "Mathématiques", "Actif"));
        when(repository.save(any(Matiere.class))).thenReturn(new Matiere("1", "Mathématiques", "Actif"));

        // Appeler la méthode à tester
        String result = service.createMatiere(request);

        // Vérifications
        assertNotNull(result);
        assertEquals("1", result);
        verify(repository, times(1)).save(any(Matiere.class));
    }

    @Test
    void testUpdateMatiere() {
        // Données de test
        MatiereRequest request = new MatiereRequest("1", "Physique", "Inactif");

        // Simuler une matière existante
        Matiere matiere = new Matiere("1", "Mathématiques", "Actif");
        when(repository.findById("1")).thenReturn(Optional.of(matiere));
        when(repository.save(any(Matiere.class))).thenReturn(matiere);

        // Appeler la méthode à tester
        String result = service.updateMatiere(request);

        // Vérifications
        assertNotNull(result);
        assertEquals("1", result);
        verify(repository, times(1)).save(any(Matiere.class));
    }

    @Test
    void testUpdateMatiereWithInvalidId() {
        // Données de test
        MatiereRequest request = new MatiereRequest("1", "Physique", "Inactif");

        // Simuler une matière non trouvée
        when(repository.findById("1")).thenReturn(Optional.empty());

        // Vérifier que l'exception est levée
        assertThrows(MatiereNotFoundException.class, () -> service.updateMatiere(request));
    }

    @Test
    void testFindAllMatieres() {
        // Simuler la réponse du repository
        when(repository.findAll()).thenReturn(List.of(
                new Matiere("1", "Mathématiques", "Actif"),
                new Matiere("2", "Physique", "Inactif")
        ));

        // Simuler la réponse du mapper
        when(mapper.fromMatiere(any(Matiere.class))).thenAnswer(invocation -> {
            Matiere matiere = invocation.getArgument(0);
            return new MatiereResponse(matiere.getId(), matiere.getLibelle(), matiere.getStatut());
        });

        // Appeler la méthode à tester
        List<MatiereResponse> result = service.findAllMatieres();

        // Vérifications
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testExistById() {
        // Simuler une matière existante
        when(repository.findById("1")).thenReturn(Optional.of(new Matiere("1", "Mathématiques", "Actif")));

        // Appeler la méthode à tester
        Boolean result = service.existById("1");

        // Vérifications
        assertTrue(result);
    }

    @Test
    void testFindById() {
        // Simuler une matière existante
        Matiere matiere = new Matiere("1", "Mathématiques", "Actif");
        when(repository.findById("1")).thenReturn(Optional.of(matiere));

        // Simuler la réponse du mapper
        when(mapper.fromMatiere(matiere)).thenReturn(new MatiereResponse("1", "Mathématiques", "Actif"));

        // Appeler la méthode à tester
        MatiereResponse result = service.findById("1");

        // Vérifications
        assertNotNull(result);
        assertEquals("1", result.id());
        assertEquals("Mathématiques", result.libelle());
        assertEquals("Actif", result.statut());
    }

    @Test
    void testFindByIdWithInvalidId() {
        // Simuler une matière non trouvée
        when(repository.findById("1")).thenReturn(Optional.empty());

        // Vérifier que l'exception est levée
        assertThrows(MatiereNotFoundException.class, () -> service.findById("1"));
    }

    @Test
    void testDeleteMatiere() {
        // Simuler la suppression
        doNothing().when(repository).deleteById("1");

        // Appeler la méthode à tester
        service.deleteMatiere("1");

        // Vérifications
        verify(repository, times(1)).deleteById("1");
    }
}
