package com.isi.institution.professeur;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProfesseurMapper {

    ProfesseurMapper INSTANCE = Mappers.getMapper(ProfesseurMapper.class);



    @Mapping(target = "id", source = "id")
    Professeur toProf(ProfesseurRequest request);



    @Mapping(target = "id", source = "id")
    ProfesseurResponse fromProf(Professeur profs);

}
