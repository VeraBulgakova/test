package com.vera.rnrc.mapper;

import com.vera.rnrc.dto.LegalEntityDTO;
import com.vera.rnrc.dto.SubjectDTO;
import com.vera.rnrc.dto.romu.EntityDTO;
import com.vera.rnrc.entity.LegalPersonEntity;
import org.springframework.stereotype.Component;

@Component
public class LegalPersonMapper {

    public LegalPersonEntity convertToLegalPerson(SubjectDTO subjectDTO, String fileName, String listName) {

        LegalEntityDTO legalEntityDTO = subjectDTO.getLegalEntityDTO();
        String inn = null;
        if (legalEntityDTO.getInn() != null) {
            inn = legalEntityDTO.getInn();
        }
        return new LegalPersonEntity(subjectDTO.getSubjectId(), fileName, listName, legalEntityDTO.getOgrn(), inn, legalEntityDTO.getFullname());
    }

    public LegalPersonEntity convertToLegalPerson(EntityDTO entityDTO, String fileName, String listName) {
        return new LegalPersonEntity(entityDTO.getDataId(), fileName, listName, null, null, entityDTO.getFirstName());
    }
}