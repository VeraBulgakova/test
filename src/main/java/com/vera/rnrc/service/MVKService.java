package com.vera.rnrc.service;

import com.vera.rnrc.dto.mvk.*;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class MVKService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;

    public MVKService(PhysicalPersonRepository physicalPersonRepository, LegalPersonRepository legalPersonRepository) {
        this.physicalPersonRepository = physicalPersonRepository;
        this.legalPersonRepository = legalPersonRepository;
    }

    @Transactional
    public void saveAll(MVKDecisionListDTO jaxbObject, String finalFileName, String type) {
        List<SubjectDTO> subjectsList = jaxbObject.getActualDecisionsList().getDecisions().stream()
                .map(DecisionDTO::getSubjectList)  // Преобразовать каждое DecisionDTO в SubjectList
                .filter(Objects::nonNull)          // Фильтровать нулевые SubjectList
                .map(SubjectList::getSubjects)     // Преобразовать каждый SubjectList в List<SubjectDTO>
                .filter(Objects::nonNull)          // Фильтровать нулевые списки субъектов
                .flatMap(List::stream)             // Преобразовать Stream<List<SubjectDTO>> в Stream<SubjectDTO>
                .toList();
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

    public PhysicalPersonEntity convertToPhysicalPersonList(SubjectDTO subject, String fileName, String listName) {
        PhysicalPersonEntity entity = new PhysicalPersonEntity();
        FLDTO physicalPerson = subject.getFl();
        List<DocumentDTO> documents = physicalPerson.getDocuments(); // Получение списка документов
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
        entity.setInn(physicalPerson.getInn());
        entity.setFullName(physicalPerson.getFullName());
        entity.setSurname(physicalPerson.getSurname());
        entity.setName(physicalPerson.getName());
        entity.setPatronymic(physicalPerson.getPatronymic());
        if (physicalPerson.getDateOfBirth() != null) {
            entity.setDateOfBirth(physicalPerson.getDateOfBirth().replaceAll("-", ""));
        } else {
            entity.setDateOfBirth(null);
        }
        entity.setDateOfBirth(physicalPerson.getDateOfBirth().replaceAll("-", ""));
        entity.setPlaceOfBirth(physicalPerson.getPlaceOfBirth());
        entity.setResidentSign(getResidentSign(subject.getSubjectType().getSubjectTypeId()));
        return entity;
    }

    public LegalPersonEntity convertToLegalEntityList(SubjectDTO subject, String fileName, String listName) {
        LegalPersonEntity entity = new LegalPersonEntity();
        LegalEntity organization = subject.getLegalEntity();

        entity.setId(String.valueOf(subject.getSubjectId()));
        entity.setDateList(fileName);
        entity.setListName(listName);
        entity.setInn(organization.getInn());
        entity.setOgrn(organization.getOgrn());
        entity.setOrganizationName(organization.getName());
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


    public DocumentDTO selectLatestDocument(List<DocumentDTO> documents) {
        return documents.stream()
                .filter(Objects::nonNull) // Фильтрация ненулевых документов
                .max(Comparator.comparing(DocumentDTO::getDateOfIssue, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null); // Возвращаем null, если нет подходящих документов

    }

}
