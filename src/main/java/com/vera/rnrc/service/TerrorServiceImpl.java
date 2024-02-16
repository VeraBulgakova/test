package com.vera.rnrc.service;

import com.vera.rnrc.dto.DocumentDTO;
import com.vera.rnrc.dto.LegalEntityDTO;
import com.vera.rnrc.dto.PhysicalPersonDTO;
import com.vera.rnrc.dto.SubjectDTO;
import com.vera.rnrc.dto.terror.TERRORPerechenDTO;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class TerrorServiceImpl implements TerrorService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;
    private static final Logger logger = LoggerFactory.getLogger(TerrorServiceImpl.class);

    @Autowired
    public TerrorServiceImpl(PhysicalPersonRepository physicalPersonRepository, LegalPersonRepository legalPersonRepository) {
        this.physicalPersonRepository = physicalPersonRepository;
        this.legalPersonRepository = legalPersonRepository;
    }

    @Transactional
    public void saveAll(TERRORPerechenDTO jaxbObject, String finalFileName, String type) {
        List<SubjectDTO> subjectsList = jaxbObject.getActualPerechenDTO().getSubjectsDTO();

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
        logger.info("{} legal persons saved", legalPersonEntities.size());

        physicalPersonRepository.saveAll(physicalPersonEntities);
        logger.info("{} physical persons saved", physicalPersonEntities.size());
    }

    @Override
    public PhysicalPersonEntity convertToPhysicalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        PhysicalPersonEntity entity = new PhysicalPersonEntity();
        PhysicalPersonDTO physicalPersonDTO = subjectDTO.getPhysicalPersonDTO();
        List<DocumentDTO> documentsDTO = physicalPersonDTO.getDocumentDTOList();
        if (documentsDTO == null || documentsDTO.isEmpty()) {
            entity.setDocNumber(null);
            entity.setDocSeries(null);
        } else {
            entity.setDocSeries(selectLatestDocument(documentsDTO).getSeries());
            entity.setDocNumber(selectLatestDocument(documentsDTO).getNumber());
        }
        entity.setId(String.valueOf(subjectDTO.getSubjectId()));
        entity.setListName(listName);
        entity.setDateList(fileName);
        entity.setInn(physicalPersonDTO.getINN());
        entity.setFullname(physicalPersonDTO.getFullName());
        entity.setLastname(physicalPersonDTO.getLastname());
        entity.setFirstname(physicalPersonDTO.getFirstname());
        entity.setMiddlename(physicalPersonDTO.getMiddlename());
        if (physicalPersonDTO.getDateOfBirth() != null) {
            entity.setDateOfBirth(physicalPersonDTO.getDateOfBirth().replaceAll("-", ""));
        }
        entity.setPlaceOfBirth(physicalPersonDTO.getPlaceOfBirth());
        return entity;
    }

    @Override
    public LegalPersonEntity convertToLegalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        LegalPersonEntity entity = new LegalPersonEntity();
        LegalEntityDTO legalEntityDTO = subjectDTO.getLegalEntityDTO();

        entity.setId(subjectDTO.getSubjectId());
        entity.setDateList(fileName);
        entity.setListName(listName);
        if (legalEntityDTO.getInn() != null) {
            entity.setInn(legalEntityDTO.getInn());
        }
        entity.setOgrn(legalEntityDTO.getOgrn());
        entity.setFullname(legalEntityDTO.getFullname());

        return entity;
    }

    @Override
    public DocumentDTO selectLatestDocument(List<DocumentDTO> documentDTOS) {
        return documentDTOS.stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(DocumentDTO::getDateOfIssue, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null);
    }
}