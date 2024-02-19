package com.vera.rnrc.service;

import com.vera.rnrc.dto.romu.EntityDTO;
import com.vera.rnrc.dto.romu.IndividualDTO;
import com.vera.rnrc.dto.romu.ROMUPerechenDTO;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import com.vera.rnrc.mapper.LegalPersonMapper;
import com.vera.rnrc.mapper.PhysicalPersonMapper;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ROMUServiceImpl implements ROMUService {
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final LegalPersonMapper legalPersonMapper;
    private final PhysicalPersonMapper physicalPersonMapper;

    @Transactional
    public void saveAll(ROMUPerechenDTO jaxbObject, String finalFileName, String type) {

        List<IndividualDTO> physicalPersonList = jaxbObject.getIndividualsDTO().getIndividual();

        List<EntityDTO> legalPersonList = jaxbObject.getEntities();

        List<PhysicalPersonEntity> physicalPersonEntities = physicalPersonList.stream()
                .map(subject -> convertToPhysicalPerson(subject, finalFileName, type))
                .toList();
        List<LegalPersonEntity> legalPersonEntities = legalPersonList.stream()
                .map(subject -> convertToLegalPerson(subject, finalFileName, type))
                .toList();

        legalPersonRepository.saveAll(legalPersonEntities);
        physicalPersonRepository.saveAll(physicalPersonEntities);
    }

    private PhysicalPersonEntity convertToPhysicalPerson(IndividualDTO individual, String fileName, String listName) {
        return physicalPersonMapper.convertToPhysicalPerson(individual, fileName, listName);
    }

    private LegalPersonEntity convertToLegalPerson(EntityDTO entityDTO, String fileName, String listName) {
        return legalPersonMapper.convertToLegalPerson(entityDTO, fileName, listName);
    }

}
