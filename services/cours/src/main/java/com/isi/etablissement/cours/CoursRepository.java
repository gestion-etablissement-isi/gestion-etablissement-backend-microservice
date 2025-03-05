package com.isi.etablissement.cours;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoursRepository extends MongoRepository<Cours, String> {
}
