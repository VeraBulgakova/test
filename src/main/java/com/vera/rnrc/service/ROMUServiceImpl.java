package com.vera.rnrc.service;

import com.vera.rnrc.dto.romu.EntityDTO;
import com.vera.rnrc.dto.romu.IndividualDTO;
import com.vera.rnrc.dto.romu.IndividualsDTO;
import com.vera.rnrc.dto.romu.ROMUPerechenDTO;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ROMUServiceImpl implements ROMUService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final LegalPersonMapper legalPersonMapper;
    private final PhysicalPersonMapper physicalPersonMapper;
    private static final Logger logger = LoggerFactory.getLogger(ROMUServiceImpl.class);

    @Transactional
    public void saveAll(ROMUPerechenDTO jaxbObject, String finalFileName, String type) {

        List<IndividualDTO> physicalPersonList = getIndividualList(jaxbObject);

        List<EntityDTO> legalPersonList = getEntities(jaxbObject);

        List<PhysicalPersonEntity> physicalPersonEntities = getPhysicalPersonEntityList(finalFileName, type, physicalPersonList);
        List<LegalPersonEntity> legalPersonEntities = getLegalPersonEntityList(finalFileName, type, legalPersonList);

        legalPersonRepository.saveAll(legalPersonEntities);
        logger.info("{} legal persons saved", legalPersonEntities.size());

        physicalPersonRepository.saveAll(physicalPersonEntities);
        logger.info("{} physical persons saved", physicalPersonEntities.size());
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

    private List<LegalPersonEntity> getLegalPersonEntityList(String finalFileName, String type, List<EntityDTO> legalPersonList) {
        return legalPersonList.stream()
                .map(subject -> convertToLegalPerson(subject, finalFileName, type))
                .toList();
    }

    private List<PhysicalPersonEntity> getPhysicalPersonEntityList(String finalFileName, String type, List<IndividualDTO> physicalPersonList) {
        return physicalPersonList.stream()
                .map(subject -> convertToPhysicalPerson(subject, finalFileName, type))
                .toList();
    }

    private PhysicalPersonEntity convertToPhysicalPerson(IndividualDTO individual, String fileName, String listName) {
        return physicalPersonMapper.convertToPhysicalPerson(individual, fileName, listName);
    }

    private LegalPersonEntity convertToLegalPerson(EntityDTO entityDTO, String fileName, String listName) {
        return legalPersonMapper.convertToLegalPerson(entityDTO, fileName, listName);
    }

}
