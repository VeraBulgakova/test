package ru.rnrc.re2.partnercheck.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.rnrc.re2.partnercheck.dto.request.RequestDTO;
import ru.rnrc.re2.partnercheck.dto.response.ResponseDTO;

import javax.xml.bind.JAXBException;
import java.time.LocalDate;
import java.util.List;

public interface ResponseService {
    @Transactional
    void createViewForPhysicPerson(String dateList, String listName);

    @Transactional
    void createViewForLegalPerson(String dateList, String listName);

    @Transactional
    List<ResponseDTO> getCheckResponseForPartners(RequestDTO request, LocalDate checkDate);

    String uploadXmlFile(MultipartFile file, String type);

    List<ResponseDTO> checkPartners(MultipartFile file, LocalDate checkDate) throws JAXBException;

}