package com.isi.institution.cours;

import com.isi.institution.classe.ClasseClient;
import com.isi.institution.classe.ClasseResponse;
import com.isi.institution.exception.BusinessException;
import com.isi.institution.exception.CoursNotFoundException;
import com.isi.institution.matiere.MatiereController;
import com.isi.institution.professeur.ProfesseurClient;
import com.isi.institution.professeur.ProfesseurResponse;
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
class CoursServiceTest {

    @Mock
    private CoursRepository repository;

    @Mock
    private CoursMapper mapper;

    @Mock
    private ProfesseurClient professeurClient;

    @Mock
    private ClasseClient classeClient;

    @Mock
    private MatiereController matiereController;

    @InjectMocks
    private CoursService service;

    @Test
    void testCreateCours() {
        // Données de test
        CoursRequest request = new CoursRequest(
                "1", "Mathématiques", 30, 2, "2023-2024", "classe1", "prof1", "matiere1"
        );

        // Simuler les réponses des clients
        when(professeurClient.findProfesseurById("prof1")).thenReturn(Optional.of(
                new ProfesseurResponse("prof1", "Jean", "Dupont", "jean.dupont@example.com", "Mathématiques", "Actif")
        ));
        when(classeClient.findClasseById("classe1")).thenReturn(Optional.of(
                new ClasseResponse("classe1", "Classe A", "2023-2024")
        ));

        // Simuler le mapper et le repository
        when(mapper.toCours(request)).thenReturn(new Cours());
        when(repository.save(any(Cours.class))).thenReturn(new Cours());

        // Appeler la méthode à tester
        String result = service.createCours(request);

        // Vérifications
        assertNotNull(result);
        verify(repository, times(1)).save(any(Cours.class));
    }

    @Test
    void testCreateCoursWithInvalidProfesseur() {
        // Données de test
        CoursRequest request = new CoursRequest(
                "1", "Mathématiques", 30, 2, "2023-2024", "classe1", "prof1", "matiere1"
        );

        // Simuler un professeur non trouvé
        when(professeurClient.findProfesseurById("prof1")).thenReturn(Optional.empty());

        // Vérifier que l'exception est levée
        assertThrows(BusinessException.class, () -> service.createCours(request));
    }

    @Test
    void testCreateCoursWithInvalidClasse() {
        // Données de test
        CoursRequest request = new CoursRequest(
                "1", "Mathématiques", 30, 2, "2023-2024", "classe1", "prof1", "matiere1"
        );

        // Simuler un professeur trouvé mais une classe non trouvée
        when(professeurClient.findProfesseurById("prof1")).thenReturn(Optional.of(
                new ProfesseurResponse("prof1", "Jean", "Dupont", "jean.dupont@example.com", "Mathématiques", "Actif")
        ));
        when(classeClient.findClasseById("classe1")).thenReturn(Optional.empty());

        // Vérifier que l'exception est levée
        assertThrows(BusinessException.class, () -> service.createCours(request));
    }

    @Test
    void testUpdateCours() {
        // Données de test
        CoursRequest request = new CoursRequest(
                "1", "Mathématiques", 30, 2, "2023-2024", "classe1", "prof1", "matiere1"
        );

        // Simuler un cours existant
        Cours cours = new Cours();
        when(repository.findById("1")).thenReturn(Optional.of(cours));

        // Simuler les réponses des clients
        when(professeurClient.findProfesseurById("prof1")).thenReturn(Optional.of(
                new ProfesseurResponse("prof1", "Jean", "Dupont", "jean.dupont@example.com", "Mathématiques", "Actif")
        ));
        when(classeClient.findClasseById("classe1")).thenReturn(Optional.of(
                new ClasseResponse("classe1", "Classe A", "2023-2024")
        ));

        // Simuler le repository
        when(repository.save(any(Cours.class))).thenReturn(cours);

        // Appeler la méthode à tester
        String result = service.updateCours(request);

        // Vérifications
        assertNotNull(result);
        verify(repository, times(1)).save(any(Cours.class));
    }

    @Test
    void testUpdateCoursWithInvalidId() {
        // Données de test
        CoursRequest request = new CoursRequest(
                "1", "Mathématiques", 30, 2, "2023-2024", "classe1", "prof1", "matiere1"
        );

        // Simuler un cours non trouvé
        when(repository.findById("1")).thenReturn(Optional.empty());

        // Vérifier que l'exception est levée
        assertThrows(CoursNotFoundException.class, () -> service.updateCours(request));
    }

    @Test
    void testFindAllCours() {
        // Simuler la réponse du repository
        when(repository.findAll()).thenReturn(List.of(new Cours()));

        // Simuler la réponse du mapper
        when(mapper.fromCours(any(Cours.class))).thenReturn(
                new CoursResponse("1", "Mathématiques", 30, 2, "2023-2024", "classe1", "prof1", "matiere1")
        );

        // Appeler la méthode à tester
        List<CoursResponse> result = service.findAllCours();

        // Vérifications
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testExistById() {
        // Simuler un cours existant
        when(repository.findById("1")).thenReturn(Optional.of(new Cours()));

        // Appeler la méthode à tester
        Boolean result = service.existById("1");

        // Vérifications
        assertTrue(result);
    }

    @Test
    void testFindById() {
        // Simuler un cours existant
        Cours cours = new Cours();
        when(repository.findById("1")).thenReturn(Optional.of(cours));

        // Simuler la réponse du mapper
        when(mapper.fromCours(cours)).thenReturn(
                new CoursResponse("1", "Mathématiques", 30, 2, "2023-2024", "classe1", "prof1", "matiere1")
        );

        // Appeler la méthode à tester
        CoursResponse result = service.findById("1");

        // Vérifications
        assertNotNull(result);
        assertEquals("1", result.id());
    }

    @Test
    void testFindByIdWithInvalidId() {
        // Simuler un cours non trouvé
        when(repository.findById("1")).thenReturn(Optional.empty());

        // Vérifier que l'exception est levée
        assertThrows(CoursNotFoundException.class, () -> service.findById("1"));
    }

    @Test
    void testDeleteCours() {
        // Simuler la suppression
        doNothing().when(repository).deleteById("1");

        // Appeler la méthode à tester
        service.deleteCours("1");

        // Vérifications
        verify(repository, times(1)).deleteById("1");
    }
}
