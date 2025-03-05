package com.isi.institution.etudiant;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EtudiantRepository extends MongoRepository<Etudiant,String> {
}
