package com.isi.institution;

import com.isi.institution.classe.ClassController;
import com.isi.institution.classe.ClasseRequest;
import com.isi.institution.classe.ClasseResponse;
import com.isi.institution.classe.ClasseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassControllerTest {

    @Mock
    private ClasseService classeService;

    @InjectMocks
    private ClassController classController;

    private ClasseRequest classeRequest;
    private ClasseResponse classeResponse;
    private String classId = "123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        classeRequest = new ClasseRequest(classId, "Mathématiques", "2024-2025");
        classeResponse = new ClasseResponse(classId, "Mathématiques", "2024-2025");
    }

    @Test
    void createClass_ShouldReturnClassId() {
        when(classeService.createClass(classeRequest)).thenReturn(classId);

        ResponseEntity<String> response = classController.createClass(classeRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(classId, response.getBody());
        verify(classeService, times(1)).createClass(classeRequest);
    }

    @Test
    void findAll_ShouldReturnListOfClasses() {
        List<ClasseResponse> classes = List.of(classeResponse);
        when(classeService.findAllClass()).thenReturn(classes);

        ResponseEntity<List<ClasseResponse>> response = classController.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(classeService, times(1)).findAllClass();
    }

    @Test
    void findById_ShouldReturnClass() {
        when(classeService.findById(classId)).thenReturn(classeResponse);

        ResponseEntity<ClasseResponse> response = classController.findById(classId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(classeResponse, response.getBody());
        verify(classeService, times(1)).findById(classId);
    }

    @Test
    void updateClass_ShouldUpdateSuccessfully() {
        doNothing().when(classeService).updateClass(classeRequest);

        ResponseEntity<Void> response = classController.updateClass(classId, classeRequest);

        assertEquals(202, response.getStatusCodeValue());
        verify(classeService, times(1)).updateClass(any(ClasseRequest.class));
    }

    @Test
    void deleteClass_ShouldDeleteSuccessfully() {
        doNothing().when(classeService).deleteClass(classId);

        ResponseEntity<Void> response = classController.delete(classId);

        assertEquals(202, response.getStatusCodeValue());
        verify(classeService, times(1)).deleteClass(classId);
    }
}
