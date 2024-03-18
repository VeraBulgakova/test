package ru.rnrc.re2.partnercheck.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rnrc.re2.partnercheck.dto.romu.EntityDTO;
import ru.rnrc.re2.partnercheck.dto.romu.IndividualDTO;
import ru.rnrc.re2.partnercheck.dto.romu.IndividualsDTO;
import ru.rnrc.re2.partnercheck.dto.romu.ROMUPerechenDTO;
import ru.rnrc.re2.partnercheck.entity.LegalPerson;
import ru.rnrc.re2.partnercheck.entity.PhysicalPerson;
import ru.rnrc.re2.partnercheck.mapper.PersonMapper;
import ru.rnrc.re2.partnercheck.mapper.PhysicalPersonMapper;
import ru.rnrc.re2.partnercheck.repository.LegalPersonRepository;
import ru.rnrc.re2.partnercheck.repository.PhysicalPersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ROMUServiceImpl implements ROMUService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final PersonMapper personMapper;
    private final PhysicalPersonMapper physicalPersonMapper;


    public void saveAll(ROMUPerechenDTO jaxbObject, String finalFileName, String type) {

        List<IndividualDTO> physicalPersonList = getIndividualList(jaxbObject);

        List<EntityDTO> legalPersonList = getEntities(jaxbObject);

        List<PhysicalPerson> physicalPersonEntities = getPhysicalPersonEntityList(finalFileName, type, physicalPersonList);
        List<LegalPerson> legalPersonEntities = getLegalPersonEntityList(finalFileName, type, legalPersonList);

        legalPersonRepository.saveAll(legalPersonEntities);

        physicalPersonRepository.saveAll(physicalPersonEntities);
    }

    private static List<EntityDTO> getEntities(ROMUPerechenDTO jaxbObject) {
        return Optional.ofNullable(jaxbObject)
                .map(ROMUPerechenDTO::getEntities)
                .orElse(List.of());
    }

    private static List<IndividualDTO> getIndividualList(ROMUPerechenDTO jaxbObject) {
        return Optional.ofNullable(jaxbObject)
                .map(ROMUPerechenDTO::getIndividualsDTO)
                .map(IndividualsDTO::getIndividual)
                .orElse(List.of());
    }

    private List<LegalPerson> getLegalPersonEntityList(String finalFileName, String type, List<EntityDTO> legalPersonList) {
        return legalPersonList.stream()
                .map(subject -> convertToLegalPerson(subject, finalFileName, type))
                .toList();
    }

    private List<PhysicalPerson> getPhysicalPersonEntityList(String finalFileName, String type, List<IndividualDTO> physicalPersonList) {
        return physicalPersonList.stream()
                .map(subject -> convertToPhysicalPerson(subject, finalFileName, type))
                .toList();
    }

    private PhysicalPerson convertToPhysicalPerson(IndividualDTO individual, String fileName, String listName) {
        return physicalPersonMapper.convertToPhysicalPerson(individual, fileName, listName);
    }

    private LegalPerson convertToLegalPerson(EntityDTO entityDTO, String fileName, String listName) {
        return personMapper.convertToLegalPerson(entityDTO, fileName, listName);
    }

}
