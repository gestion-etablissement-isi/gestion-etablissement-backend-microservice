package com.isi.institution.description;

import com.isi.institution.exception.DescriptionNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DescriptionServiceTest {

    @Mock
    private DescriptionRepository repository;

    @Mock
    private DescriptionMapper mapper;

    @InjectMocks
    private DescriptionService service;

    @Test
    void testCreateDescription() {
        // Données de test
        DescriptionRequest request = new DescriptionRequest(
                "1", LocalDate.of(2023, 10, 15), "09:00", "11:00", "Cours de mathématiques"
        );

        // Simuler le mapper et le repository
        when(mapper.toDescription(request)).thenReturn(new Description("1", LocalDate.of(2023, 10, 15), "09:00", "11:00", "Cours de mathématiques"));
        when(repository.save(any(Description.class))).thenReturn(new Description("1", LocalDate.of(2023, 10, 15), "09:00", "11:00", "Cours de mathématiques"));

        // Appeler la méthode à tester
        String result = service.createDescription(request);

        // Vérifications
        assertNotNull(result);
        assertEquals("1", result);
        verify(repository, times(1)).save(any(Description.class));
    }

    @Test
    void testUpdateDescription() {
        // Données de test
        DescriptionRequest request = new DescriptionRequest(
                "1", LocalDate.of(2023, 10, 15), "10:00", "12:00", "Cours de physique"
        );

        // Simuler une description existante
        Description description = new Description("1", LocalDate.of(2023, 10, 15), "09:00", "11:00", "Cours de mathématiques");
        when(repository.findById("1")).thenReturn(Optional.of(description));
        when(repository.save(any(Description.class))).thenReturn(description);

        // Appeler la méthode à tester
        String result = service.updateDescription(request);

        // Vérifications
        assertNotNull(result);
        assertEquals("1", result);
        verify(repository, times(1)).save(any(Description.class));
    }

    @Test
    void testUpdateDescriptionWithInvalidId() {
        // Données de test
        DescriptionRequest request = new DescriptionRequest(
                "1", LocalDate.of(2023, 10, 15), "10:00", "12:00", "Cours de physique"
        );

        // Simuler une description non trouvée
        when(repository.findById("1")).thenReturn(Optional.empty());

        // Vérifier que l'exception est levée
        assertThrows(DescriptionNotFoundException.class, () -> service.updateDescription(request));
    }

    @Test
    void testFindAllDescription() {
        // Simuler la réponse du repository
        when(repository.findAll()).thenReturn(List.of(
                new Description("1", LocalDate.of(2023, 10, 15), "09:00", "11:00", "Cours de mathématiques"),
                new Description("2", LocalDate.of(2023, 10, 16), "10:00", "12:00", "Cours de physique")
        ));

        // Simuler la réponse du mapper
        when(mapper.fromDescription(any(Description.class))).thenAnswer(invocation -> {
            Description description = invocation.getArgument(0);
            return new DescriptionResponse(
                    description.getId(),
                    description.getDateCours(),
                    description.getHeureDebut(),
                    description.getHeureFin(),
                    description.getDescription()
            );
        });

        // Appeler la méthode à tester
        List<DescriptionResponse> result = service.findAllDescription();

        // Vérifications
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testExistById() {
        // Simuler une description existante
        when(repository.findById("1")).thenReturn(Optional.of(new Description("1", LocalDate.of(2023, 10, 15), "09:00", "11:00", "Cours de mathématiques")));

        // Appeler la méthode à tester
        Boolean result = service.existById("1");

        // Vérifications
        assertTrue(result);
    }

    @Test
    void testFindById() {
        // Simuler une description existante
        Description description = new Description("1", LocalDate.of(2023, 10, 15), "09:00", "11:00", "Cours de mathématiques");
        when(repository.findById("1")).thenReturn(Optional.of(description));

        // Simuler la réponse du mapper
        when(mapper.fromDescription(description)).thenReturn(new DescriptionResponse("1", LocalDate.of(2023, 10, 15), "09:00", "11:00", "Cours de mathématiques"));

        // Appeler la méthode à tester
        DescriptionResponse result = service.findById("1");

        // Vérifications
        assertNotNull(result);
        assertEquals("1", result.id());
        assertEquals(LocalDate.of(2023, 10, 15), result.dateCours());
        assertEquals("09:00", result.heureDebut());
        assertEquals("11:00", result.heureFin());
        assertEquals("Cours de mathématiques", result.description());
    }

    @Test
    void testFindByIdWithInvalidId() {
        // Simuler une description non trouvée
        when(repository.findById("1")).thenReturn(Optional.empty());

        // Vérifier que l'exception est levée
        assertThrows(DescriptionNotFoundException.class, () -> service.findById("1"));
    }

    @Test
    void testDeleteDescription() {
        // Simuler la suppression
        doNothing().when(repository).deleteById("1");

        // Appeler la méthode à tester
        service.deleteDescription("1");

        // Vérifications
        verify(repository, times(1)).deleteById("1");
    }
}
