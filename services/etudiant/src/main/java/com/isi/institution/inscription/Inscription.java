package com.isi.institution.etudiant;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "inscriptions")

public class Inscription {

    @Id
    private String id;
    private String etudiantId;
    private String classeId;
    private String anneeScolaire;
}
