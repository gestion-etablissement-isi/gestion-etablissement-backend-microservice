package com.isi.institution;

import com.isi.institution.classe.*;
import com.isi.institution.exception.ClasseNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassServiceTest {

    @Mock
    private ClasseRepository repository;

    @Mock
    private ClasseMapper mapper;

    @InjectMocks
    private ClasseService service;

    private ClasseEntity classe;
    private ClasseRequest request;
    private ClasseResponse response;
    private final String ID = "classe123";

    @BeforeEach
    void setUp() {
        classe = new ClasseEntity();
        classe.setId(ID);
        classe.setNom("Terminale S");
        classe.setAnnee_scolaire("2023-2024");

        request = new ClasseRequest(ID, "Terminale S", "2023-2024");
        response = new ClasseResponse(ID, "Terminale S", "2023-2024");
    }

    @Test
    void testCreateClass() {
        // Préparation
        when(mapper.toClasseEntity(any(ClasseRequest.class))).thenReturn(classe);
        when(repository.save(any(ClasseEntity.class))).thenReturn(classe);

        // Exécution
        String result = service.createClass(request);

        // Vérification
        assertEquals(ID, result);
        verify(mapper, times(1)).toClasseEntity(request);
        verify(repository, times(1)).save(classe);
    }

    @Test
    void testFindAllClass() {
        // Préparation
        List<ClasseEntity> classes = Arrays.asList(classe);
        when(repository.findAll()).thenReturn(classes);
        when(mapper.fromClasseEntity(any(ClasseEntity.class))).thenReturn(response);

        // Exécution
        List<ClasseResponse> result = service.findAllClass();

        // Vérification
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).fromClasseEntity(classe);
    }

    @Test
    void testFindByIdWithExistingId() {
        // Préparation
        when(repository.findById(ID)).thenReturn(Optional.of(classe));
        when(mapper.fromClasseEntity(classe)).thenReturn(response);

        // Exécution
        ClasseResponse result = service.findById(ID);

        // Vérification
        assertEquals(response, result);
        verify(repository, times(1)).findById(ID);
        verify(mapper, times(1)).fromClasseEntity(classe);
    }

    @Test
    void testFindByIdWithNonExistingId() {
        // Préparation
        String nonExistingId = "nonExistingId";
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Exécution et vérification
        Exception exception = assertThrows(ClasseNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });

        assertTrue(exception.getMessage().contains(nonExistingId));
        verify(repository, times(1)).findById(nonExistingId);
        verify(mapper, never()).fromClasseEntity(any());
    }

    @Test
    void testUpdateClassWithExistingId() {
        // Préparation
        when(repository.findById(ID)).thenReturn(Optional.of(classe));

        // Exécution
        service.updateClass(request);

        // Vérification
        verify(repository, times(1)).findById(ID);
        verify(repository, times(1)).save(classe);
        assertEquals(request.nom(), classe.getNom());
        assertEquals(request.annee_scolaire(), classe.getAnnee_scolaire());
    }

    @Test
    void testUpdateClassWithNonExistingId() {
        // Préparation
        String nonExistingId = "nonExistingId";
        ClasseRequest invalidRequest = new ClasseRequest(nonExistingId, "Terminale S", "2023-2024");
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Exécution et vérification
        Exception exception = assertThrows(ClasseNotFoundException.class, () -> {
            service.updateClass(invalidRequest);
        });

        assertTrue(exception.getMessage().contains(nonExistingId));
        verify(repository, times(1)).findById(nonExistingId);
        verify(repository, never()).save(any());
    }

    @Test
    void testPartialUpdateClass() {
        // Préparation
        ClasseRequest partialRequest = new ClasseRequest(ID, "Première S", null);
        when(repository.findById(ID)).thenReturn(Optional.of(classe));

        // Exécution
        service.updateClass(partialRequest);

        // Vérification
        verify(repository, times(1)).findById(ID);
        verify(repository, times(1)).save(classe);
        assertEquals("Première S", classe.getNom());
        assertEquals("2023-2024", classe.getAnnee_scolaire()); // Doit rester inchangé
    }

    @Test
    void testDeleteClass() {
        // Préparation
        doNothing().when(repository).deleteById(ID);

        // Exécution
        service.deleteClass(ID);

        // Vérification
        verify(repository, times(1)).deleteById(ID);
    }
}