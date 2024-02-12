package com.vera.rnrc.service;

import com.vera.rnrc.dto.DocumentDTO;
import com.vera.rnrc.dto.SubjectDTO;
import com.vera.rnrc.dto.terror.TERRORPerechenDTO;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;

import java.util.List;

public interface TerrorService {
    void saveAll(TERRORPerechenDTO jaxbObject, String finalFileName, String type);


    PhysicalPersonEntity convertToPhysicalPerson(SubjectDTO subjectDTO, String fileName, String listName);


    LegalPersonEntity convertToLegalPerson(SubjectDTO subjectDTO, String fileName, String listName);


    DocumentDTO selectLatestDocument(List<DocumentDTO> documentDTOS);
}
