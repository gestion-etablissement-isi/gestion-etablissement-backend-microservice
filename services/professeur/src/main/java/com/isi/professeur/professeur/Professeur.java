package com.isi.professeur.professeur;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Professeur {
    @Id
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String statut;
    private String matiereId;

}
