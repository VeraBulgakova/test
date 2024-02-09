package com.vera.rnrc.service;

import com.vera.rnrc.dto.DocumentDTO;
import com.vera.rnrc.dto.SubjectDTO;
import com.vera.rnrc.dto.mvk.DecisionDTO;
import com.vera.rnrc.dto.mvk.MVKPerechenDTO;
import com.vera.rnrc.dto.mvk.SubjectListDTO;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MVKServiceImpl implements PerechenService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;

    public MVKServiceImpl(PhysicalPersonRepository physicalPersonRepository, LegalPersonRepository legalPersonRepository) {
        this.physicalPersonRepository = physicalPersonRepository;
        this.legalPersonRepository = legalPersonRepository;
    }


    public void saveAll(MVKPerechenDTO jaxbObject, String finalFileName, String type) {
        List<SubjectDTO> subjectsList = jaxbObject.getActualDecisionsListDTO().getDecisions().stream()
                .map(DecisionDTO::getSubjectListDTO)
                .filter(Objects::nonNull)
                .map(SubjectListDTO::getSubjectsDTO)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .toList();

        List<PhysicalPersonEntity> physicalPersonEntities = new ArrayList<>();
        List<LegalPersonEntity> legalPersonEntities = new ArrayList<>();

        for (SubjectDTO subjectDTO : subjectsList) {
            if (subjectDTO.getSubjectTypeDTO().getName().contains("Физическое лицо")) {
                physicalPersonEntities.add(convertToPhysicalPerson(subjectDTO, finalFileName, type));
            } else if (subjectDTO.getSubjectTypeDTO().getName().contains("Юридическое лицо")) {
                legalPersonEntities.add(convertToLegalPerson(subjectDTO, finalFileName, type));
            }
        }

        legalPersonRepository.saveAll(legalPersonEntities);
        physicalPersonRepository.saveAll(physicalPersonEntities);
    }

    @Override
    public PhysicalPersonEntity convertToPhysicalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        return PerechenService.super.convertToPhysicalPerson(subjectDTO, fileName, listName);
    }

    @Override
    public LegalPersonEntity convertToLegalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        return PerechenService.super.convertToLegalPerson(subjectDTO, fileName, listName);
    }

    @Override
    public DocumentDTO selectLatestDocument(List<DocumentDTO> documentDTOS) {
        return PerechenService.super.selectLatestDocument(documentDTOS);
    }
}
