package com.isi.professeur.professeur;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfesseurRepository extends MongoRepository<Professeur, String> {
}
