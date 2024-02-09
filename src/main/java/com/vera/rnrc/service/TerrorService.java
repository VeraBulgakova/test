package com.vera.rnrc.service;

import com.vera.rnrc.dto.DocumentDTO;
import com.vera.rnrc.dto.SubjectDTO;
import com.vera.rnrc.dto.terror.TERRORPerechen;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<SubjectDTO> subjectsList = jaxbObject.getActualPerechen().getSubjectsDTO();

        List<PhysicalPersonEntity> physicalPersonEntities = new ArrayList<>();
        List<LegalPersonEntity> legalPersonEntities = new ArrayList<>();

        for (SubjectDTO subjectDTO : subjectsList) {
            if (subjectDTO.getSubjectTypeDTO().getName().contains("Физическое лицо")) {
                physicalPersonEntities.add(convertToPhysicalPersonList(subjectDTO, finalFileName, type));
            } else if (subjectDTO.getSubjectTypeDTO().getName().contains("Юридическое лицо")) {
                legalPersonEntities.add(convertToLegalEntityList(subjectDTO, finalFileName, type));
            }
        }

        legalPersonRepository.saveAll(legalPersonEntities);
        physicalPersonRepository.saveAll(physicalPersonEntities);
    }

    @Override
    public PhysicalPersonEntity convertToPhysicalPersonList(SubjectDTO subjectDTO, String fileName, String listName) {
        return PerechenService.super.convertToPhysicalPersonList(subjectDTO, fileName, listName);
    }

    @Override
    public LegalPersonEntity convertToLegalEntityList(SubjectDTO subjectDTO, String fileName, String listName) {
        return PerechenService.super.convertToLegalEntityList(subjectDTO, fileName, listName);
    }

    @Override
    public DocumentDTO selectLatestDocument(List<DocumentDTO> documentDTOS) {
        return PerechenService.super.selectLatestDocument(documentDTOS);
    }
}