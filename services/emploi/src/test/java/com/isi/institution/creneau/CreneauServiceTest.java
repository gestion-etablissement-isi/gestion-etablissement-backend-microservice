package com.isi.institution.creneau;

import com.isi.institution.cours.CoursClient;
import com.isi.institution.cours.CoursResponse;
import com.isi.institution.description.Description;
import com.isi.institution.description.DescriptionController;
import com.isi.institution.description.DescriptionRequest;
import com.isi.institution.exception.BusinessException;
import com.isi.institution.exception.CreneauNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreneauServiceTest {

    @Mock
    private CreneauRepository repository;

    @Mock
    private CreneauMapper mapper;

    @Mock
    private CoursClient coursClient;

    @Mock
    private DescriptionController descriptionController;

    @InjectMocks
    private CreneauService creneauService;

    private CreneauRequest creneauRequest;
    private Creneau creneau;
    private Description description;

    @BeforeEach
    void setUp() {
        description = new Description("desc1", LocalDate.now(), "08:00", "10:00", "Cours de mathématiques");
        creneauRequest = new CreneauRequest("1", "cours1", List.of(description));
        creneau = new Creneau("1", "cours1", List.of(description));
    }

    @Test
    void shouldCreateCreneauSuccessfully() {
        when(coursClient.findCoursById(creneauRequest.coursId())).thenReturn(Optional.of(new CoursResponse("cours1", "Maths", 30, 4, "2024", "classe1", "prof1", "matiere1")));
        when(mapper.toCreneau(creneauRequest)).thenReturn(creneau);
        when(repository.save(creneau)).thenReturn(creneau);
        doNothing().when(descriptionController).createDescription(any(DescriptionRequest.class));

        String result = creneauService.createCreneau(creneauRequest);

        assertThat(result).isEqualTo("1");
        verify(repository).save(creneau);
        verify(descriptionController, times(1)).createDescription(any(DescriptionRequest.class));
    }

    @Test
    void shouldThrowExceptionWhenCoursNotFound() {
        when(coursClient.findCoursById(creneauRequest.coursId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> creneauService.createCreneau(creneauRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Impossible de creer un creneau");
    }

    @Test
    void shouldUpdateCreneauSuccessfully() {
        when(repository.findById(creneauRequest.id())).thenReturn(Optional.of(creneau));
        when(repository.save(creneau)).thenReturn(creneau);

        String result = creneauService.updateCreneau(creneauRequest);

        assertThat(result).isEqualTo("1");
        verify(repository).save(creneau);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentCreneau() {
        when(repository.findById(creneauRequest.id())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> creneauService.updateCreneau(creneauRequest))
                .isInstanceOf(CreneauNotFoundException.class)
                .hasMessageContaining("Impossible de modifier le créneau");
    }

    @Test
    void shouldFindCreneauById() {
        when(repository.findById("1")).thenReturn(Optional.of(creneau));
        when(mapper.fromCreneau(creneau)).thenReturn(new CreneauResponse("1", "cours1", List.of(description)));

        CreneauResponse response = creneauService.findById("1");

        assertThat(response.id()).isEqualTo("1");
        verify(repository).findById("1");
    }

    @Test
    void shouldThrowExceptionWhenCreneauNotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> creneauService.findById("1"))
                .isInstanceOf(CreneauNotFoundException.class)
                .hasMessageContaining("Impossible de trouver le creneau");
    }

    @Test
    void shouldDeleteCreneauSuccessfully() {
        doNothing().when(repository).deleteById("1");

        creneauService.deleteCreneau("1");

        verify(repository).deleteById("1");
    }
}
