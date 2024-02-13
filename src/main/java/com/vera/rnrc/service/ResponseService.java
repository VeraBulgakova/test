package com.vera.rnrc.service;

import com.vera.rnrc.dto.request.RequestDTO;
import com.vera.rnrc.dto.response.ResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ResponseService {
    @Transactional
    void createViewForPhysicPerson(String dateList, String listName);

    @Transactional
    void createViewForLegalPerson(String dateList, String listName);

    @Transactional
    List<ResponseDTO> getCheckResponseForPartners(RequestDTO request, LocalDate checkDate);

}
