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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ROMUService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;

    public ROMUService(PhysicalPersonRepository physicalPersonRepository, LegalPersonRepository legalPersonRepository) {
        this.physicalPersonRepository = physicalPersonRepository;
        this.legalPersonRepository = legalPersonRepository;
    }

    @Transactional
    public void saveAll(ROMUPerechen jaxbObject, String finalFileName, String type) {

        List<IndividualDTO> physicalPersonList = jaxbObject.getIndividuals().getIndividual();

        List<EntityDTO> legalPersonList = jaxbObject.getEntities();

        List<PhysicalPersonEntity> physicalPersonEntities = physicalPersonList.stream()
                .map(subject -> convertToPhysicalPersonList(subject, finalFileName, type))
                .toList();
        List<LegalPersonEntity> legalPersonEntities = legalPersonList.stream()
                .map(subject -> convertToLegalEntityList(subject, finalFileName, type))
                .toList();

        saveAllLegalPerson(legalPersonEntities);
        saveAllPhysicalPersons(physicalPersonEntities);
    }

    public void saveAllPhysicalPersons(List<PhysicalPersonEntity> physicalPersons) {
        physicalPersonRepository.saveAll(physicalPersons);
    }

    public void saveAllLegalPerson(List<LegalPersonEntity> legalPersons) {
        legalPersonRepository.saveAll(legalPersons);
    }

    public PhysicalPersonEntity convertToPhysicalPersonList(IndividualDTO individual, String fileName, String listName) {
        PhysicalPersonEntity entity = new PhysicalPersonEntity();

        entity.setPassportNumber(individual.getDocument().getNumber());
        entity.setId(String.valueOf(individual.getDataId()));
        entity.setListName(listName);
        entity.setDateList(fileName);
        entity.setFullName(individual.getFullName());
        entity.setSurname(individual.getFirstName());
        IndividualDateOfBirthDTO dateOfBirthDTO = individual.getDateOfBirth();
        if (dateOfBirthDTO != null && dateOfBirthDTO.getDate() != null) {
            entity.setDateOfBirth(dateOfBirthDTO.getDate().replace("-", ""));
        }
        return entity;
    }

    public LegalPersonEntity convertToLegalEntityList(EntityDTO entityDTO, String fileName, String listName) {
        LegalPersonEntity entity = new LegalPersonEntity();

        entity.setId(String.valueOf(entityDTO.getDataId()));
        entity.setDateList(fileName);
        entity.setListName(listName);
        entity.setOrganizationName(entityDTO.getFirstName());

        return entity;
    }

}
