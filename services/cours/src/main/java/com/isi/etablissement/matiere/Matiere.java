package com.isi.etablissement.matiere;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Matiere {
    @Id
    private String id;
    private String libelle;
    private String statut;
}
