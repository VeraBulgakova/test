package com.vera.rnrc.service;

import com.vera.rnrc.dto.terror.*;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class TerrorService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;

    @Autowired
    public TerrorService(PhysicalPersonRepository physicalPersonRepository, LegalPersonRepository legalPersonRepository) {
        this.physicalPersonRepository = physicalPersonRepository;
        this.legalPersonRepository = legalPersonRepository;
    }

    @Transactional
    public void saveAll(Perechen jaxbObject, String finalFileName, String type) {

//        switch (type) {
//            case "Террор":
//                Perechen perechen = processXmlFile(file, Perechen.class, true);
//                subjectService.saveAll(perechen, fileName, type);
//                break;
//            case "МВК":
//                MVKDecisionListDTO MVKDecisionList = processXmlFile(file, MVKDecisionListDTO.class, false);
//                subjectService.saveAll(MVKDecisionList, fileName, type);
//                break;
//            case "РОМУ":
//                ConsolidatedListDTO consolidatedListDTO = processXmlFile(file, ConsolidatedListDTO.class, true);
//                subjectService.saveAll(consolidatedListDTO, fileName, type);
//                break;
//            default:
//                return ResponseEntity.badRequest().body("Неизвестный тип файла");
//        }

        List<Subject> subjectsList = jaxbObject.getActualPerechen().getSubjects();
        List<PhysicalPersonEntity> physicalPersonEntities = subjectsList.stream()
                .filter(subject -> subject.getSubjectType().getName().contains("Физическое лицо"))
                .map(subject -> convertToPhysicalPersonList(subject, finalFileName, type))
                .toList();
        List<LegalPersonEntity> legalPersonEntities = subjectsList.stream()
                .filter(subject -> subject.getSubjectType().getName().contains("Юридическое лицо"))
                .map(subject -> convertToLegalEntityList(subject, finalFileName, type))
                .toList();

        saveAllLegalPerson(legalPersonEntities);
        saveAllPhysicalPersons(physicalPersonEntities);
    }

    @Transactional
    public void saveAllPhysicalPersons(List<PhysicalPersonEntity> physicalPersons) {
        physicalPersonRepository.saveAll(physicalPersons);
    }

    @Transactional
    public void saveAllLegalPerson(List<LegalPersonEntity> legalPersons) {
        legalPersonRepository.saveAll(legalPersons);
    }

    public PhysicalPersonEntity convertToPhysicalPersonList(Subject subject, String fileName, String listName) {
        PhysicalPersonEntity entity = new PhysicalPersonEntity();
        FL physicalPerson = subject.getFl();
        List<Document> documents = physicalPerson.getDocumentList(); // Получение списка документов
        if (documents == null || documents.isEmpty()) {
            entity.setPassportSeries("Нет данных");
            entity.setPassportNumber("Нет данных");
        } else {
            entity.setPassportSeries(selectLatestDocument(documents).getSeries());
            entity.setPassportNumber(selectLatestDocument(documents).getNumber());
        }
        entity.setId(String.valueOf(subject.getSubjectId()));
        entity.setListName(listName);
        entity.setDateList(fileName);
        entity.setInn(defaultIfNullOrEmptyOrShort(physicalPerson.getINN()));
        entity.setFullName(defaultIfNullOrEmptyOrShort(physicalPerson.getFullName()));
        entity.setSurname(defaultIfNullOrEmptyOrShort(physicalPerson.getSurname()));
        entity.setName(defaultIfNullOrEmptyOrShort(physicalPerson.getName()));
        entity.setPatronymic(defaultIfNullOrEmptyOrShort(physicalPerson.getPatronymic()));
        entity.setDateOfBirth(defaultIfNullOrEmptyOrShort(physicalPerson.getDateOfBirth()));
        entity.setPlaceOfBirth(defaultIfNullOrEmptyOrShort(physicalPerson.getPlaceOfBirth()));
        entity.setResidentSign(getResidentSign(subject.getSubjectType().getSubjectTypeId()));
        return entity;
    }

    public LegalPersonEntity convertToLegalEntityList(Subject subject, String fileName, String listName) {
        LegalPersonEntity entity = new LegalPersonEntity();
        Organization organization = subject.getOrganization();

        entity.setId(String.valueOf(subject.getSubjectId()));
        entity.setDateList(fileName);
        entity.setListName(listName);
        entity.setInn(defaultIfNullOrEmptyOrShort(organization.getInn()));
        entity.setOgrn(defaultIfNullOrEmptyOrShort(organization.getOgrn()));
        entity.setOrganizationName(defaultIfNullOrEmptyOrShort(organization.getOrganizationName()));
        entity.setResidentSign(getResidentSign(subject.getSubjectType().getSubjectTypeId()));

        return entity;
    }

    public String getResidentSign(long subjectTypeId) {
        if (subjectTypeId == 1 || subjectTypeId == 2) {
            return "Нерезидент";
        } else if (subjectTypeId == 3 || subjectTypeId == 4) {
            return "Резидент";
        } else return "Нет данных";
    }


    public String defaultIfNullOrEmptyOrShort(String value) {
        return (value == null || value.trim().length() < 2) ? "Нет данных" : value;
    }

    public Document selectLatestDocument(List<Document> documents) {
        return documents.stream()
                .filter(Objects::nonNull) // Фильтрация ненулевых документов
                .max(Comparator.comparing(Document::getDateOfIssue, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null); // Возвращаем null, если нет подходящих документов

    }
}
