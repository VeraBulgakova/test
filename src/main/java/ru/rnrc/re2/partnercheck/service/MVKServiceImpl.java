package ru.rnrc.re2.partnercheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rnrc.re2.partnercheck.dto.SubjectDTO;
import ru.rnrc.re2.partnercheck.dto.mvk.ActualDecisionsListDTO;
import ru.rnrc.re2.partnercheck.dto.mvk.DecisionDTO;
import ru.rnrc.re2.partnercheck.dto.mvk.MVKPerechenDTO;
import ru.rnrc.re2.partnercheck.dto.mvk.SubjectListDTO;
import ru.rnrc.re2.partnercheck.entity.LegalPerson;
import ru.rnrc.re2.partnercheck.entity.PhysicalPerson;
import ru.rnrc.re2.partnercheck.mapper.PersonMapper;
import ru.rnrc.re2.partnercheck.mapper.PhysicalPersonMapper;
import ru.rnrc.re2.partnercheck.repository.LegalPersonRepository;
import ru.rnrc.re2.partnercheck.repository.PhysicalPersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MVKServiceImpl implements MVKService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final PersonMapper personMapper;
    private final PhysicalPersonMapper physicalPersonMapper;

    public void saveAll(MVKPerechenDTO jaxbObject, String finalFileName, String type) {
        List<SubjectDTO> subjectsList = getSubjectsList(jaxbObject);

        List<PhysicalPerson> physicalPersonEntities = new ArrayList<>();
        List<LegalPerson> legalPersonEntities = new ArrayList<>();

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
        return Optional.ofNullable(jaxbObject.getActualDecisionsListDTO())
                .map(ActualDecisionsListDTO::getDecisions)
                .stream()
                .flatMap(List::stream)
                .map(DecisionDTO::getSubjectListDTO)
                .filter(Objects::nonNull)
                .map(SubjectListDTO::getSubjectsDTO)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .toList();
    }

    private PhysicalPerson convertToPhysicalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        return physicalPersonMapper.convertToPhysicalPerson(subjectDTO, fileName, listName);
    }

    private LegalPerson convertToLegalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        return personMapper.convertToLegalPerson(subjectDTO, fileName, listName);
    }
}