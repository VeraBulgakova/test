package com.vera.rnrc.service;

import com.vera.rnrc.dto.romu.EntityDTO;
import com.vera.rnrc.dto.romu.IndividualDTO;
import com.vera.rnrc.dto.romu.IndividualDateOfBirthDTO;
import com.vera.rnrc.dto.romu.ROMUPerechen;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ROMUServiceImpl implements ROMUService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;
    private static final Logger logger = LoggerFactory.getLogger(ROMUServiceImpl.class);

    public ROMUServiceImpl(PhysicalPersonRepository physicalPersonRepository, LegalPersonRepository legalPersonRepository) {
        this.physicalPersonRepository = physicalPersonRepository;
        this.legalPersonRepository = legalPersonRepository;
    }

    @Transactional
    public void saveAll(ROMUPerechen jaxbObject, String finalFileName, String type) {

        List<IndividualDTO> physicalPersonList = jaxbObject.getIndividuals().getIndividual();

        List<EntityDTO> legalPersonList = jaxbObject.getEntities();

        List<PhysicalPersonEntity> physicalPersonEntities = physicalPersonList.stream()
                .map(subject -> convertToPhysicalPerson(subject, finalFileName, type))
                .toList();
        List<LegalPersonEntity> legalPersonEntities = legalPersonList.stream()
                .map(subject -> convertToLegalPerson(subject, finalFileName, type))
                .toList();

        legalPersonRepository.saveAll(legalPersonEntities);
        logger.info("{} legal persons saved", legalPersonEntities.size());

        physicalPersonRepository.saveAll(physicalPersonEntities);
        logger.info("{} physical persons saved", physicalPersonEntities.size());
    }

    public PhysicalPersonEntity convertToPhysicalPerson(IndividualDTO individual, String fileName, String listName) {
        PhysicalPersonEntity entity = new PhysicalPersonEntity();

        entity.setDocNumber(individual.getDocument().getNumber());
        entity.setId(String.valueOf(individual.getDataId()));
        entity.setListName(listName);
        entity.setDateList(fileName);
        entity.setFullname(individual.getFullName());
        entity.setFirstname(individual.getFirstName());
        IndividualDateOfBirthDTO dateOfBirthDTO = individual.getDateOfBirth();
        if (dateOfBirthDTO != null && dateOfBirthDTO.getDate() != null) {
            entity.setDateOfBirth(dateOfBirthDTO.getDate().replace("-", ""));
        }
        return entity;
    }

    public LegalPersonEntity convertToLegalPerson(EntityDTO entityDTO, String fileName, String listName) {
        LegalPersonEntity entity = new LegalPersonEntity();

        entity.setId(entityDTO.getDataId());
        entity.setDateList(fileName);
        entity.setListName(listName);
        entity.setFullname(entityDTO.getFirstName());

        return entity;
    }

}
