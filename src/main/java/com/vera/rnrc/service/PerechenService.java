package com.vera.rnrc.service;

import com.vera.rnrc.dto.DocumentDTO;
import com.vera.rnrc.dto.LegalEntityDTO;
import com.vera.rnrc.dto.PhysicalPersonDTO;
import com.vera.rnrc.dto.SubjectDTO;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public interface PerechenService {
    default PhysicalPersonEntity convertToPhysicalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        PhysicalPersonEntity entity = new PhysicalPersonEntity();
        PhysicalPersonDTO physicalPersonDTO = subjectDTO.getPhysicalPersonDTO();
        List<DocumentDTO> documentsDTO = physicalPersonDTO.getDocumentDTOList();
        if (documentsDTO == null || documentsDTO.isEmpty()) {
            entity.setDocNumber(null);
            entity.setDocSeries(null);
        } else {
            entity.setDocSeries(selectLatestDocument(documentsDTO).getSeries());
            entity.setDocNumber(selectLatestDocument(documentsDTO).getNumber());
        }
        entity.setId(String.valueOf(subjectDTO.getSubjectId()));
        entity.setListName(listName);
        entity.setDateList(fileName);
        entity.setInn(physicalPersonDTO.getINN());
        entity.setFullname(physicalPersonDTO.getFullName());
        entity.setLastname(physicalPersonDTO.getLastname());
        entity.setFirstname(physicalPersonDTO.getFirstname());
        entity.setMiddlename(physicalPersonDTO.getMiddlename());
        if (physicalPersonDTO.getDateOfBirth() != null) {
            entity.setDateOfBirth(physicalPersonDTO.getDateOfBirth().replaceAll("-", ""));
        }
        entity.setPlaceOfBirth(physicalPersonDTO.getPlaceOfBirth());
        return entity;
    }

    default LegalPersonEntity convertToLegalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
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

    default DocumentDTO selectLatestDocument(List<DocumentDTO> documentDTOS) {
        return documentDTOS.stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(DocumentDTO::getDateOfIssue, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null);
    }
}
