package com.vera.rnrc.service;

import com.vera.rnrc.dto.Document;
import com.vera.rnrc.dto.Subject;
import com.vera.rnrc.dto.mvk.DecisionDTO;
import com.vera.rnrc.dto.mvk.MVKPerechen;
import com.vera.rnrc.dto.mvk.SubjectList;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MVKService implements PerechenService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;

    public MVKService(PhysicalPersonRepository physicalPersonRepository, LegalPersonRepository legalPersonRepository) {
        this.physicalPersonRepository = physicalPersonRepository;
        this.legalPersonRepository = legalPersonRepository;
    }


    @Transactional
    public void saveAll(MVKPerechen jaxbObject, String finalFileName, String type) {
        List<Subject> subjectsList = jaxbObject.getActualDecisionsList().getDecisions().stream()
                .map(DecisionDTO::getSubjectList)
                .filter(Objects::nonNull)
                .map(SubjectList::getSubjects)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .toList();

        List<PhysicalPersonEntity> physicalPersonEntities = new ArrayList<>();
        List<LegalPersonEntity> legalPersonEntities = new ArrayList<>();

        for (Subject subject : subjectsList) {
            if (subject.getSubjectType().getName().contains("Физическое лицо")) {
                physicalPersonEntities.add(convertToPhysicalPersonList(subject, finalFileName, type));
            } else if (subject.getSubjectType().getName().contains("Юридическое лицо")) {
                legalPersonEntities.add(convertToLegalEntityList(subject, finalFileName, type));
            }
        }

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
