package com.isi.institution.salle;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SalleRepository  extends MongoRepository<Salle,String> {
}
