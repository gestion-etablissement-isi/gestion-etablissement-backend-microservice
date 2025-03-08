package com.isi.institution;

import com.isi.institution.exception.SalleNotFoundException;
import com.isi.institution.salle.*;
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
 class SalleServiceTest {

    @Mock
    private SalleRepository repository;

    @Mock
    private SalleMapper mapper;

    @InjectMocks
    private SalleService service;

    private Salle salle;
    private SalleRequest request;
    private SalleResponse response;
    private final String ID = "salle123";

    @BeforeEach
    void setUp() {
        salle = new Salle();
        salle.setId(ID);
        salle.setNom("Salle Physique");

        request = new SalleRequest(ID, "Salle Physique");
        response = new SalleResponse(ID, "Salle Physique");
    }

    @Test
    void testCreateSalle() {
        // Préparation
        when(mapper.toSalle(any(SalleRequest.class))).thenReturn(salle);
        when(repository.save(any(Salle.class))).thenReturn(salle);

        // Exécution
        String result = service.createSalle(request);

        // Vérification
        assertEquals(ID, result);
        verify(mapper, times(1)).toSalle(request);
        verify(repository, times(1)).save(salle);
    }

    @Test
    void testFindAllSalles() {
        // Préparation
        List<Salle> salles = Arrays.asList(salle);
        when(repository.findAll()).thenReturn(salles);
        when(mapper.fromSalle(any(Salle.class))).thenReturn(response);

        // Exécution
        List<SalleResponse> result = service.findAllSalles();

        // Vérification
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).fromSalle(salle);
    }

    @Test
    void testFindByIdWithExistingId() {
        // Préparation
        when(repository.findById(ID)).thenReturn(Optional.of(salle));
        when(mapper.fromSalle(salle)).thenReturn(response);

        // Exécution
        SalleResponse result = service.findById(ID);

        // Vérification
        assertEquals(response, result);
        verify(repository, times(1)).findById(ID);
        verify(mapper, times(1)).fromSalle(salle);
    }

    @Test
    void testFindByIdWithNonExistingId() {
        // Préparation
        String nonExistingId = "nonExistingId";
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Exécution et vérification
        Exception exception = assertThrows(SalleNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });

        assertTrue(exception.getMessage().contains(nonExistingId));
        verify(repository, times(1)).findById(nonExistingId);
        verify(mapper, never()).fromSalle(any());
    }

    @Test
    void testUpdateSalleWithExistingId() {
        // Préparation
        when(repository.findById(ID)).thenReturn(Optional.of(salle));

        // Exécution
        service.updateSalle(request);

        // Vérification
        verify(repository, times(1)).findById(ID);
        verify(repository, times(1)).save(salle);
        assertEquals(request.nom(), salle.getNom());
    }

    @Test
    void testUpdateSalleWithNonExistingId() {
        // Préparation
        String nonExistingId = "nonExistingId";
        SalleRequest invalidRequest = new SalleRequest(nonExistingId, "Salle Chimie");
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Exécution et vérification
        Exception exception = assertThrows(SalleNotFoundException.class, () -> {
            service.updateSalle(invalidRequest);
        });

        assertTrue(exception.getMessage().contains(nonExistingId));
        verify(repository, times(1)).findById(nonExistingId);
        verify(repository, never()).save(any());
    }

    @Test
    void testDeleteSalle() {
        // Préparation
        doNothing().when(repository).deleteById(ID);

        // Exécution
        service.deleteSalle(ID);

        // Vérification
        verify(repository, times(1)).deleteById(ID);
    }
}