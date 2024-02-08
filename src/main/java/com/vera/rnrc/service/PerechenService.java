package com.vera.rnrc.service;

import com.vera.rnrc.dto.Document;
import com.vera.rnrc.dto.LegalEntity;
import com.vera.rnrc.dto.PhysicalPerson;
import com.vera.rnrc.dto.Subject;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public interface PerechenService {
    default PhysicalPersonEntity convertToPhysicalPersonList(Subject subject, String fileName, String listName) {
        PhysicalPersonEntity entity = new PhysicalPersonEntity();
        PhysicalPerson physicalPerson = subject.getPhysicalPerson();
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
        }
        entity.setPlaceOfBirth(physicalPerson.getPlaceOfBirth());
        return entity;
    }

    default LegalPersonEntity convertToLegalEntityList(Subject subject, String fileName, String listName) {
        LegalPersonEntity entity = new LegalPersonEntity();
        LegalEntity legalEntity = subject.getLegalEntity();

        entity.setId(String.valueOf(subject.getSubjectId()));
        entity.setDateList(fileName);
        entity.setListName(listName);
        if (legalEntity.getInn() != null) {
            entity.setInn(legalEntity.getInn());
        }
        entity.setOgrn(legalEntity.getOgrn());
        entity.setOrganizationName(legalEntity.getOrganizationName());

        return entity;
    }

    default Document selectLatestDocument(List<Document> documents) {
        return documents.stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(Document::getDateOfIssue, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null);
    }
}
