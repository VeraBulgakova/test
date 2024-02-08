package com.vera.rnrc.service;

import com.vera.rnrc.dto.Document;
import com.vera.rnrc.dto.Subject;
import com.vera.rnrc.dto.terror.TERRORPerechen;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerrorService implements PerechenService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;

    @Autowired
    public TerrorService(PhysicalPersonRepository physicalPersonRepository, LegalPersonRepository legalPersonRepository) {
        this.physicalPersonRepository = physicalPersonRepository;
        this.legalPersonRepository = legalPersonRepository;
    }

    @Transactional
    public void saveAll(TERRORPerechen jaxbObject, String finalFileName, String type) {
        List<Subject> subjectsList = jaxbObject.getActualPerechen().getSubjects();
        List<PhysicalPersonEntity> physicalPersonEntities = subjectsList.stream()
                .filter(subject -> subject.getSubjectType().getName().contains("Физическое лицо"))
                .map(subject -> convertToPhysicalPersonList(subject, finalFileName, type))
                .toList();
        List<LegalPersonEntity> legalPersonEntities = subjectsList.stream()
                .filter(subject -> subject.getSubjectType().getName().contains("Юридическое лицо"))
                .map(subject -> convertToLegalEntityList(subject, finalFileName, type))
                .toList();

        legalPersonRepository.saveAll(legalPersonEntities);
        physicalPersonRepository.saveAll(physicalPersonEntities);
    }

    @Override
    public PhysicalPersonEntity convertToPhysicalPersonList(Subject subject, String fileName, String listName) {
        return PerechenService.super.convertToPhysicalPersonList(subject, fileName, listName);
    }

    @Override
    public LegalPersonEntity convertToLegalEntityList(Subject subject, String fileName, String listName) {
        return PerechenService.super.convertToLegalEntityList(subject, fileName, listName);
    }

    @Override
    public Document selectLatestDocument(List<Document> documents) {
        return PerechenService.super.selectLatestDocument(documents);
    }
}