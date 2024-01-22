package com.vera.rnrc.service;

import com.vera.rnrc.dto.terror.Document;
import com.vera.rnrc.dto.terror.FL;
import com.vera.rnrc.dto.terror.Organization;
import com.vera.rnrc.dto.terror.Subject;
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
public class SubjectService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;

    @Autowired
    public SubjectService(PhysicalPersonRepository physicalPersonRepository, LegalPersonRepository legalPersonRepository) {
        this.physicalPersonRepository = physicalPersonRepository;
        this.legalPersonRepository = legalPersonRepository;
    }

    @Transactional
    public List<PhysicalPersonEntity> saveAllPhysicalPersons(List<PhysicalPersonEntity> physicalPersons) {
        return physicalPersonRepository.saveAll(physicalPersons);
    }

    @Transactional
    public List<LegalPersonEntity> saveAllLegalPerson(List<LegalPersonEntity> legalPersons) {
        return legalPersonRepository.saveAll(legalPersons);
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
        entity.setId(subject.getSubjectId());
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

        entity.setId(subject.getSubjectId());
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

