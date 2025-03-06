package com.isi.institution.description;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DescriptionRepository extends MongoRepository<Description, String> {
}
