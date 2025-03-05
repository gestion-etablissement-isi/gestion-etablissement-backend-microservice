package com.isi.institution.inscription;



import com.isi.institution.etudiant.Inscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends MongoRepository<Inscription, String> {
    boolean existsByEtudiantIdAndAnneeScolaire(String etudiantId, String anneeScolaire);

}