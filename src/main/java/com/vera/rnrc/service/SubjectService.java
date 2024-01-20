package com.vera.rnrc.service;

import com.vera.rnrc.dto.terror.Document;
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

    public PhysicalPersonEntity savePhysicalPerson(PhysicalPersonEntity physicalPerson) {
        return physicalPersonRepository.save(physicalPerson);
    }

    public Document selectLatestDocument(List<Document> documents) {
        return documents.stream()
                .filter(Objects::nonNull) // Фильтрация ненулевых документов
                .max(Comparator.comparing(Document::getDateOfIssue, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null); // Возвращаем null, если нет подходящих документов

    }
}

