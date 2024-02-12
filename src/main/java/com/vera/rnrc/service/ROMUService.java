package com.vera.rnrc.service;

import com.vera.rnrc.dto.romu.EntityDTO;
import com.vera.rnrc.dto.romu.IndividualDTO;
import com.vera.rnrc.dto.romu.ROMUPerechen;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;

public interface ROMUService {
    void saveAll(ROMUPerechen jaxbObject, String finalFileName, String type);

    PhysicalPersonEntity convertToPhysicalPerson(IndividualDTO individual, String fileName, String listName);

    LegalPersonEntity convertToLegalPerson(EntityDTO entityDTO, String fileName, String listName);
}
