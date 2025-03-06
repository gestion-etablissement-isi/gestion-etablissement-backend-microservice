package com.isi.institution.description;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Description {
    @Id
    private String id;
    private LocalDate dateCours;
    private String heureDebut;
    private String heureFin;
    private String description;
}
