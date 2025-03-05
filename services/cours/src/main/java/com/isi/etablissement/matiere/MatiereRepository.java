package com.isi.etablissement.matiere;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatiereRepository extends MongoRepository<Matiere, String> {
}
