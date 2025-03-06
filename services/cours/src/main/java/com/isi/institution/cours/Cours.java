package com.isi.institution.cours;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    @Document
public class Cours {
    @Id
    private String id;
    private String titre;
    private int volumeHoraire;
    private int coefficient;
    private String anneeAcademique;
    private String matiereId;
    private String professeurId;
    private String classeId;


    }
