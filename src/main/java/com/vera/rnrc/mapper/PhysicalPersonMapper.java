package com.vera.rnrc.mapper;

import com.vera.rnrc.dto.DocumentDTO;
import com.vera.rnrc.dto.PhysicalPersonDTO;
import com.vera.rnrc.dto.SubjectDTO;
import com.vera.rnrc.dto.romu.IndividualDTO;
import com.vera.rnrc.dto.romu.IndividualDateOfBirthDTO;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
public class PhysicalPersonMapper {
    public PhysicalPersonEntity convertToPhysicalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
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

    public PhysicalPersonEntity convertToPhysicalPerson(IndividualDTO individual, String fileName, String listName) {
        PhysicalPersonEntity entity = new PhysicalPersonEntity();

        entity.setDocNumber(individual.getDocument().getNumber());
        entity.setId(String.valueOf(individual.getDataId()));
        entity.setListName(listName);
        entity.setDateList(fileName);
        entity.setFullname(individual.getFullName());
        entity.setFirstname(individual.getFirstName());
        IndividualDateOfBirthDTO dateOfBirthDTO = individual.getDateOfBirth();
        if (dateOfBirthDTO != null && dateOfBirthDTO.getDate() != null) {
            entity.setDateOfBirth(dateOfBirthDTO.getDate().replace("-", ""));
        }
        return entity;
    }

    private DocumentDTO selectLatestDocument(List<DocumentDTO> documentDTO) {
        return documentDTO
                .stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(DocumentDTO::getDateOfIssue, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null);
    }
}
