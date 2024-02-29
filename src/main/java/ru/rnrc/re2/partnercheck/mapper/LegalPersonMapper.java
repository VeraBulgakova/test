package ru.rnrc.re2.partnercheck.mapper;

import org.springframework.stereotype.Component;
import ru.rnrc.re2.partnercheck.dto.LegalEntityDTO;
import ru.rnrc.re2.partnercheck.dto.SubjectDTO;
import ru.rnrc.re2.partnercheck.dto.romu.EntityDTO;
import ru.rnrc.re2.partnercheck.entity.LegalPerson;

@Component
public class LegalPersonMapper {

    public LegalPerson convertToLegalPerson(SubjectDTO subjectDTO, String fileName, String listName) {

        LegalEntityDTO legalEntityDTO = subjectDTO.getLegalEntityDTO();
        String inn = null;
        if (legalEntityDTO.getInn() != null) {
            inn = legalEntityDTO.getInn();
        }
        return new LegalPerson(subjectDTO.getSubjectId(), fileName, listName, legalEntityDTO.getOgrn(), inn, legalEntityDTO.getFullname());
    }

    public LegalPerson convertToLegalPerson(EntityDTO entityDTO, String fileName, String listName) {
        return new LegalPerson(entityDTO.getDataId(), fileName, listName, null, null, entityDTO.getFirstName());
    }
}