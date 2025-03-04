package com.isi.institution.salle;

import com.isi.institution.classe.ClasseEntity;
import com.isi.institution.classe.ClasseMapper;
import com.isi.institution.classe.ClasseRequest;
import com.isi.institution.classe.ClasseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface SalleMapper {

    SalleMapper INSTANCE = Mappers.getMapper(SalleMapper.class);


    @Mapping(target = "id", source = "id")
    Salle toSalle(SalleRequest request);


    @Mapping(target = "id", source = "id")
    SalleResponse fromSalle(Salle salle);
}
