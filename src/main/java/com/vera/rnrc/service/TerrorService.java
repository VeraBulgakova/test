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

    public void saveAllPhysicalPersons(List<PhysicalPersonEntity> physicalPersons) {
        physicalPersonRepository.saveAll(physicalPersons);
    }

    public void saveAllLegalPerson(List<LegalPersonEntity> legalPersons) {
        legalPersonRepository.saveAll(legalPersons);
    }

    public PhysicalPersonEntity convertToPhysicalPersonList(Subject subject, String fileName, String listName) {
        PhysicalPersonEntity entity = new PhysicalPersonEntity();
        FL physicalPerson = subject.getFl();
        List<Document> documents = physicalPerson.getDocumentList(); // Получение списка документов
        if (documents == null || documents.isEmpty()) {
            entity.setPassportSeries(null);
            entity.setPassportNumber(null);
        } else {
            entity.setPassportSeries(selectLatestDocument(documents).getSeries());
            entity.setPassportNumber(selectLatestDocument(documents).getNumber());
        }
        entity.setId(String.valueOf(subject.getSubjectId()));
        entity.setListName(listName);
        entity.setDateList(fileName);
        entity.setInn(physicalPerson.getINN());
        entity.setFullName(physicalPerson.getFullName());
        entity.setSurname(physicalPerson.getSurname());
        entity.setName(physicalPerson.getName());
        entity.setPatronymic(physicalPerson.getPatronymic());
        if (physicalPerson.getDateOfBirth() != null) {
            entity.setDateOfBirth(physicalPerson.getDateOfBirth().replaceAll("-", ""));
        } else{
            entity.setDateOfBirth(null);
        }
        entity.setPlaceOfBirth(physicalPerson.getPlaceOfBirth());
        entity.setResidentSign(getResidentSign(subject.getSubjectType().getSubjectTypeId()));
        return entity;
    }

    public LegalPersonEntity convertToLegalEntityList(Subject subject, String fileName, String listName) {
        LegalPersonEntity entity = new LegalPersonEntity();
        Organization organization = subject.getOrganization();

        entity.setId(String.valueOf(subject.getSubjectId()));
        entity.setDateList(fileName);
        entity.setListName(listName);
        entity.setInn(organization.getInn());
        entity.setOgrn(organization.getOgrn());
        entity.setOrganizationName(organization.getOrganizationName());
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


    public Document selectLatestDocument(List<Document> documents) {
        return documents.stream()
                .filter(Objects::nonNull) // Фильтрация ненулевых документов
                .max(Comparator.comparing(Document::getDateOfIssue, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null); // Возвращаем null, если нет подходящих документов

    }
}
