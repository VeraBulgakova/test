package com.vera.rnrc.mapper;

import com.vera.rnrc.dto.LegalEntityDTO;
import com.vera.rnrc.dto.SubjectDTO;
import com.vera.rnrc.dto.romu.EntityDTO;
import com.vera.rnrc.entity.LegalPersonEntity;
import org.springframework.stereotype.Component;

@Component
public class LegalPersonMapper {

    public LegalPersonEntity convertToLegalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        LegalPersonEntity entity = new LegalPersonEntity();
        LegalEntityDTO legalEntityDTO = subjectDTO.getLegalEntityDTO();

        entity.setId(subjectDTO.getSubjectId());
        entity.setDateList(fileName);
        entity.setListName(listName);
        if (legalEntityDTO.getInn() != null) {
            entity.setInn(legalEntityDTO.getInn());
        }
        entity.setOgrn(legalEntityDTO.getOgrn());
        entity.setFullname(legalEntityDTO.getFullname());

        return entity;
    }
   public LegalPersonEntity convertToLegalPerson(EntityDTO entityDTO, String fileName, String listName) {
       LegalPersonEntity entity = new LegalPersonEntity();

       entity.setId(entityDTO.getDataId());
       entity.setDateList(fileName);
       entity.setListName(listName);
       entity.setFullname(entityDTO.getFirstName());

       return entity;
   }
}