package com.isi.institution.classe;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface ClasseMapper {

    ClasseMapper INSTANCE = Mappers.getMapper(ClasseMapper.class);

    // Conversion de ClasseRequest -> ClasseEntity
    @Mapping(target = "id", source = "id")
    ClasseEntity toClasseEntity(ClasseRequest request);

    // Conversion de ClasseEntity -> ClasseResponse
    @Mapping(target = "id", source = "id")
    ClasseResponse fromClasseEntity(ClasseEntity classeEntity);
}
