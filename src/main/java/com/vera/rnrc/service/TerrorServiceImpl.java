package com.vera.rnrc.service;

import com.vera.rnrc.dto.SubjectDTO;
import com.vera.rnrc.dto.terror.ActualPerechenDTO;
import com.vera.rnrc.dto.terror.TERRORPerechenDTO;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import com.vera.rnrc.mapper.LegalPersonMapper;
import com.vera.rnrc.mapper.PhysicalPersonMapper;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TerrorServiceImpl implements TerrorService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final LegalPersonMapper legalPersonMapper;
    private final PhysicalPersonMapper physicalPersonMapper;
    private static final Logger logger = LoggerFactory.getLogger(TerrorServiceImpl.class);


    @Transactional
    public void saveAll(TERRORPerechenDTO jaxbObject, String finalFileName, String type) {
        List<SubjectDTO> subjectsList = getSubjectsList(jaxbObject);

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

    private List<SubjectDTO> getSubjectsList(TERRORPerechenDTO jaxbObject) {
        return Optional.ofNullable(jaxbObject)
                .map(TERRORPerechenDTO::getActualPerechenDTO)
                .map(ActualPerechenDTO::getSubjectsDTO)
                .orElse(List.of());
    }

    private PhysicalPersonEntity convertToPhysicalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        return physicalPersonMapper.convertToPhysicalPerson(subjectDTO, fileName, listName);
    }


    private LegalPersonEntity convertToLegalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        return legalPersonMapper.convertToLegalPerson(subjectDTO, fileName, listName);
    }

}
