package com.isi.institution.etudiant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EtudiantMapper {

    @Mapping(target = "id", source = "id")
    Etudiant toStudentEntity(EtudiantRequest request);

    @Mapping(target = "id", source = "id")
    EtudiantResponse fromStudentEntity(Etudiant etudiant);

}
