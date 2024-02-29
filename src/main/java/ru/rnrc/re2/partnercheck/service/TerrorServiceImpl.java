package ru.rnrc.re2.partnercheck.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rnrc.re2.partnercheck.dto.SubjectDTO;
import ru.rnrc.re2.partnercheck.dto.terror.ActualPerechenDTO;
import ru.rnrc.re2.partnercheck.dto.terror.TERRORPerechenDTO;
import ru.rnrc.re2.partnercheck.entity.LegalPerson;
import ru.rnrc.re2.partnercheck.entity.PhysicalPerson;
import ru.rnrc.re2.partnercheck.mapper.LegalPersonMapper;
import ru.rnrc.re2.partnercheck.mapper.PhysicalPersonMapper;
import ru.rnrc.re2.partnercheck.repository.LegalPersonRepository;
import ru.rnrc.re2.partnercheck.repository.PhysicalPersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TerrorServiceImpl implements TerrorService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final LegalPersonMapper legalPersonMapper;
    private final PhysicalPersonMapper physicalPersonMapper;


    public void saveAll(TERRORPerechenDTO jaxbObject, String finalFileName, String type) {
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

    private List<SubjectDTO> getSubjectsList(TERRORPerechenDTO jaxbObject) {
        return Optional.ofNullable(jaxbObject)
                .map(TERRORPerechenDTO::getActualPerechenDTO)
                .map(ActualPerechenDTO::getSubjectsDTO)
                .orElse(List.of());
    }

    private PhysicalPerson convertToPhysicalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        return physicalPersonMapper.convertToPhysicalPerson(subjectDTO, fileName, listName);
    }


    private LegalPerson convertToLegalPerson(SubjectDTO subjectDTO, String fileName, String listName) {
        return legalPersonMapper.convertToLegalPerson(subjectDTO, fileName, listName);
    }

}
