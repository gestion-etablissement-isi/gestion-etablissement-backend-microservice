package com.isi.institution.classe;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class ClasseEntity {
    @Id
    private String id;
    private String nom;
    private int capacite;
    private int effectif = 0;
    private String annee_scolaire;
}
