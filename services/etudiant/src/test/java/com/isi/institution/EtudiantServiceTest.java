package com.isi.institution;

import com.isi.institution.etudiant.*;
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
	private final String ID = "etudiant123";

	@BeforeEach
	void setUp() {
		etudiant = new Etudiant();
		etudiant.setId(ID);
		etudiant.setNom("Diallo");
		etudiant.setPrenom("Mamadou");
		etudiant.setEmail("mamadou.diallo@example.com");
		etudiant.setTel("771234567");

		request = new EtudiantRequest("Diallo", "Mamadou", "mamadou.diallo@example.com", "771234567");
		response = new EtudiantResponse(ID, "Diallo", "Mamadou", "mamadou.diallo@example.com", "771234567");
	}

	@Test
	void testCreateStudent() {
		// Préparation
		when(mapper.toStudentEntity(any(EtudiantRequest.class))).thenReturn(etudiant);
		when(repository.save(any(Etudiant.class))).thenReturn(etudiant);

		// Exécution
		String result = service.createStudent(request);

		// Vérification
		assertEquals(ID, result);
		verify(mapper, times(1)).toStudentEntity(request);
		verify(repository, times(1)).save(etudiant);
	}

	@Test
	void testFindAllStudents() {
		// Préparation
		List<Etudiant> etudiants = Arrays.asList(etudiant);
		when(repository.findAll()).thenReturn(etudiants);
		when(mapper.fromStudentEntity(any(Etudiant.class))).thenReturn(response);

		// Exécution
		List<EtudiantResponse> result = service.findAllStudents();

		// Vérification
		assertEquals(1, result.size());
		assertEquals(response, result.get(0));
		verify(repository, times(1)).findAll();
		verify(mapper, times(1)).fromStudentEntity(etudiant);
	}

	@Test
	void testFindByIdWithExistingId() {
		// Préparation
		when(repository.findById(ID)).thenReturn(Optional.of(etudiant));
		when(mapper.fromStudentEntity(etudiant)).thenReturn(response);

		// Exécution
		EtudiantResponse result = service.findById(ID);

		// Vérification
		assertEquals(response, result);
		verify(repository, times(1)).findById(ID);
		verify(mapper, times(1)).fromStudentEntity(etudiant);
	}

	@Test
	void testFindByIdWithNonExistingId() {
		// Préparation
		String nonExistingId = "nonExistingId";
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		// Exécution et vérification
		Exception exception = assertThrows(RuntimeException.class, () -> service.findById(nonExistingId));

		assertEquals("Étudiant non trouvé", exception.getMessage());
		verify(repository, times(1)).findById(nonExistingId);
		verify(mapper, never()).fromStudentEntity(any());
	}

	@Test
	void testUpdateStudentWithExistingId() {
		// Préparation
		when(repository.findById(ID)).thenReturn(Optional.of(etudiant));

		// Exécution
		service.updateStudent(ID, request);

		// Vérification
		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(etudiant);
		assertEquals(request.nom(), etudiant.getNom());
		assertEquals(request.prenom(), etudiant.getPrenom());
		assertEquals(request.email(), etudiant.getEmail());
		assertEquals(request.tel(), etudiant.getTel());
	}

	@Test
	void testUpdateStudentWithNonExistingId() {
		// Préparation
		String nonExistingId = "nonExistingId";
		EtudiantRequest invalidRequest = new EtudiantRequest("Ba", "Amadou", "amadou.ba@example.com", "778765432");
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		// Exécution et vérification
		Exception exception = assertThrows(RuntimeException.class, () -> service.updateStudent(nonExistingId, invalidRequest));

		assertEquals("Étudiant non trouvé", exception.getMessage());
		verify(repository, times(1)).findById(nonExistingId);
		verify(repository, never()).save(any());
	}

	@Test
	void testDeleteStudentWithExistingId() {
		// Préparation
		doNothing().when(repository).deleteById(ID);
		when(repository.findById(ID)).thenReturn(Optional.of(etudiant));

		// Exécution
		service.deleteStudent(ID);

		// Vérification
		verify(repository, times(1)).deleteById(ID);
	}

	@Test
	void testDeleteStudentWithNonExistingId() {
		// Préparation
		String nonExistingId = "nonExistingId";
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		// Exécution et vérification
		Exception exception = assertThrows(RuntimeException.class, () -> service.deleteStudent(nonExistingId));

		assertEquals("Étudiant non trouvé", exception.getMessage());
		verify(repository, times(1)).findById(nonExistingId);
		verify(repository, never()).deleteById(any());
	}
}
