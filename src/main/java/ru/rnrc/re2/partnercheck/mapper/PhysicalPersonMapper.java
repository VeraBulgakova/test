package ru.rnrc.re2.partnercheck.mapper;

import org.springframework.stereotype.Component;
import ru.rnrc.re2.partnercheck.dto.DocumentDTO;
import ru.rnrc.re2.partnercheck.dto.PhysicalPersonDTO;
import ru.rnrc.re2.partnercheck.dto.SubjectDTO;
import ru.rnrc.re2.partnercheck.dto.romu.IndividualDTO;
import ru.rnrc.re2.partnercheck.dto.romu.IndividualDateOfBirthDTO;
import ru.rnrc.re2.partnercheck.entity.PhysicalPerson;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
public class PhysicalPersonMapper {
    public PhysicalPerson convertToPhysicalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
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
        return new PhysicalPerson(Long.parseLong(subjectDTO.getSubjectId()),
                fileName,
                listName,
                physicalPersonDTO.getInn(),
                docSeries,
                docNumber,
                physicalPersonDTO.getFullName(),
                physicalPersonDTO.getLastname(),
                physicalPersonDTO.getFirstname(),
                physicalPersonDTO.getMiddlename(),
                dateOfBirth,
                physicalPersonDTO.getPlaceOfBirth());

    }

    public PhysicalPerson convertToPhysicalPerson(IndividualDTO individual, String fileName, String listName) {
        String dateOfBirth = null;
        IndividualDateOfBirthDTO dateOfBirthDTO = individual.getDateOfBirth();
        if (dateOfBirthDTO != null && dateOfBirthDTO.getDate() != null) {
            dateOfBirth = dateOfBirthDTO.getDate().replace("-", "");
        }
        return new PhysicalPerson(
                individual.getDataId(),
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
