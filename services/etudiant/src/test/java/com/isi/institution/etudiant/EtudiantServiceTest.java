package com.isi.institution.etudiant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository repository;

    @Mock
    private EtudiantMapper mapper;

    @InjectMocks
    private EtudiantService service;

    private Etudiant etudiant;
    private EtudiantRequest request;
    private EtudiantResponse response;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant("1", "Fall", "Souleymane", "souleymane@email.com", "777777777");
        request = new EtudiantRequest("1", "Fall", "Souleymane", "souleymane@email.com", "777777777");
        response = new EtudiantResponse("1", "Fall", "Souleymane", "souleymane@email.com", "777777777");
    }

    @Test
    void testCreateStudent() {
        when(mapper.toStudentEntity(request)).thenReturn(etudiant);
        when(repository.save(etudiant)).thenReturn(etudiant);

        String studentId = service.createStudent(request);

        assertEquals("1", studentId);
        verify(repository, times(1)).save(etudiant);
    }

    @Test
    void testFindAllStudents() {
        when(repository.findAll()).thenReturn(List.of(etudiant));
        when(mapper.fromStudentEntity(etudiant)).thenReturn(response);

        List<EtudiantResponse> students = service.findAllStudents();

        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
        assertEquals("1", students.get(0).id());
    }

    @Test
    void testFindById_Success() {
        when(repository.findById("1")).thenReturn(Optional.of(etudiant));
        when(mapper.fromStudentEntity(etudiant)).thenReturn(response);

        EtudiantResponse foundStudent = service.findById("1");

        assertNotNull(foundStudent);
        assertEquals("1", foundStudent.id());
    }

    @Test
    void testFindById_NotFound() {
        when(repository.findById("2")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> service.findById("2"));
        assertEquals("Étudiant non trouvé", exception.getMessage());
    }

    @Test
    void testUpdateStudent() {
        when(repository.findById("1")).thenReturn(Optional.of(etudiant));
        when(repository.save(any(Etudiant.class))).thenReturn(etudiant);

        service.updateStudent("1", request);

        verify(repository, times(1)).save(etudiant);
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(repository).deleteById("1");

        service.deleteStudent("1");

        verify(repository, times(1)).deleteById("1");
    }
}
