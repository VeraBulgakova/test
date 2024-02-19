package com.vera.rnrc.service;

import com.vera.rnrc.dto.SubjectDTO;
import com.vera.rnrc.dto.mvk.DecisionDTO;
import com.vera.rnrc.dto.mvk.MVKPerechenDTO;
import com.vera.rnrc.dto.mvk.SubjectListDTO;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import com.vera.rnrc.mapper.LegalPersonMapper;
import com.vera.rnrc.mapper.PhysicalPersonMapper;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MVKServiceImpl implements MVKService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final LegalPersonMapper legalPersonMapper;
    private final PhysicalPersonMapper physicalPersonMapper;

    @Transactional
    public void saveAll(MVKPerechenDTO jaxbObject, String finalFileName, String type) {
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
        physicalPersonRepository.saveAll(physicalPersonEntities);
    }

    private List<SubjectDTO> getSubjectsList(MVKPerechenDTO jaxbObject) {
        return jaxbObject.getActualDecisionsListDTO().getDecisions().stream()
                .map(DecisionDTO::getSubjectListDTO)
                .filter(Objects::nonNull)
                .map(SubjectListDTO::getSubjectsDTO)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .toList();
    }

    private PhysicalPersonEntity convertToPhysicalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        return physicalPersonMapper.convertToPhysicalPerson(subjectDTO, fileName, listName);
    }

    private LegalPersonEntity convertToLegalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        return legalPersonMapper.convertToLegalPerson(subjectDTO, fileName, listName);
    }
}