package com.isi.institution.inscription;

import com.isi.institution.etudiant.Etudiant;
import com.isi.institution.etudiant.EtudiantRequest;
import com.isi.institution.etudiant.EtudiantResponse;
import com.isi.institution.etudiant.Inscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InscriptionMapper {

    @Mapping(target = "id", ignore = true )
    Inscription toInscription(InscriptionRequest request);
    @Mapping(target = "id", source = "id")

    InscriptionResponse fromInscription(Inscription inscription);

}
