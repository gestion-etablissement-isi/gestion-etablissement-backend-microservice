package com.isi.institution.creneau;

import com.isi.institution.description.Description;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Creneau {
    @Id
    private String id;
    private String coursId;
    private List<Description> descriptions;
}
