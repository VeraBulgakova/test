package com.vera.rnrc.service;

import com.vera.rnrc.dto.request.RequestDTO;
import com.vera.rnrc.dto.response.ResponseDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ResponseService {
    @Transactional
    void createViewForPhysicPerson(@Param("dateList") String dateList, @Param("listName") String listName);

    @Transactional
    void createViewForLegalPerson(@Param("dateList") String dateList, @Param("listName") String listName);

    @Transactional
    List<ResponseDTO> getCheckResponseForPartners(RequestDTO request, LocalDate checkDate);

}
