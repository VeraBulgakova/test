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
        String docNumber = null;
        String docSeries = null;
        String dateOfBirth = null;

        PhysicalPersonDTO physicalPersonDTO = subjectDTO.getPhysicalPersonDTO();

        List<DocumentDTO> documentsDTO = physicalPersonDTO.getDocumentDTOList();

        if (documentsDTO != null) {
            docSeries = selectLatestDocument(documentsDTO).getSeries();
            docNumber = selectLatestDocument(documentsDTO).getNumber();
        }

        if (physicalPersonDTO.getDateOfBirth() != null) {
            dateOfBirth = physicalPersonDTO.getDateOfBirth().replaceAll("-", "");
        }
        return new PhysicalPersonEntity(String.valueOf(subjectDTO.getSubjectId()),
                fileName,
                listName,
                physicalPersonDTO.getINN(),
                docSeries,
                docNumber,
                physicalPersonDTO.getFullName(),
                physicalPersonDTO.getLastname(),
                physicalPersonDTO.getFirstname(),
                physicalPersonDTO.getMiddlename(),
                dateOfBirth,
                physicalPersonDTO.getPlaceOfBirth());

    }

    public PhysicalPersonEntity convertToPhysicalPerson(IndividualDTO individual, String fileName, String listName) {
        String dateOfBirth = null;
        IndividualDateOfBirthDTO dateOfBirthDTO = individual.getDateOfBirth();
        if (dateOfBirthDTO != null && dateOfBirthDTO.getDate() != null) {
            dateOfBirth = dateOfBirthDTO.getDate().replace("-", "");
        }
        return new PhysicalPersonEntity(
                String.valueOf(individual.getDataId()),
                fileName,
                listName,
                null,
                null,
                individual.getDocument().getNumber(),
                individual.getFullName(),
                null,
                individual.getFirstName(),
                null,
                dateOfBirth,
                null

        );
    }

    private DocumentDTO selectLatestDocument(List<DocumentDTO> documentDTO) {
        return documentDTO
                .stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(DocumentDTO::getDateOfIssue, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null);
    }
}
