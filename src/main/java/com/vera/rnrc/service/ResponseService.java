package com.vera.rnrc.service;

import com.vera.rnrc.dto.request.QlikViewRequest;
import com.vera.rnrc.dto.response.ResponseDTO;
import com.vera.rnrc.entity.ResponseEntity;
import com.vera.rnrc.repository.ResponseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ResponseService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    @Transactional
    public void createViewForPhysicPerson(@Param("dateList") String dateList, @Param("listName") String listName) {
        String sql = "CREATE or replace VIEW viewPhysical AS " +
                "SELECT p.id, iop.pzinskey as partner_id " +
                "FROM index_olr_partnerlinkedstructure iop " +
                "         JOIN physical_person p ON " +
                "    p.inn = iop.inn " +
                "        AND p.list_name = '" + listName.replace("'", "''") + "' " +
                "        AND p.date_list = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT p.id, iop.pzinskey as partner_id " +
                "FROM index_olr_partnerlinkedstructure iop " +
                "         JOIN physical_person p ON " +
                "    p.passport_series = iop.docseries AND p.passport_number = iop.docnumber " +
                "        AND p.list_name = '" + listName.replace("'", "''") + "' " +
                "        AND p.date_list = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT p.id, iop.pzinskey as partner_id " +
                "FROM index_olr_partnerlinkedstructure iop " +
                "         JOIN physical_person p ON " +
                "    p.date_of_birth = iop.dateofbirth " +
//                "        and LOWER(p.place_of_birth) = LOWER(iop.placeofbirth) " +
                "        AND LOWER(p.surname) = LOWER(iop.lastname) AND LOWER(p.name) = LOWER(iop.firstname) " +
                "        AND LOWER(p.patronymic) = LOWER(iop.middlename) " +
                "        AND p.list_name = '" + listName.replace("'", "''") + "' " +
                "        AND p.date_list = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT p.id, iop.pzinskey as partner_id " +
                "FROM index_olr_partnerlinkedstructure iop " +
                "         JOIN physical_person p ON " +
                "    p.date_of_birth = iop.dateofbirth " +
//                "        and LOWER(p.place_of_birth) = LOWER(iop.placeofbirth) " +
                "        AND LOWER(p.full_name) = LOWER(iop.fullname) " +
                "        AND p.list_name = '" + listName.replace("'", "''") + "' " +
                "        AND p.date_list = '" + dateList.replace("'", "''") + "' ;";
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Transactional
    public void createViewForLegalPerson(@Param("dateList") String dateList, @Param("listName") String listName) {
        String sql = "CREATE or replace VIEW viewLegal AS " +
                "SELECT l.id, r.id as partner_id " +
                "FROM rnrc_ref_partner r " +
                "         JOIN legal_person l ON " +
                "        l.inn = r.inn " +
                "        AND l.list_name = '" + listName.replace("'", "''") + "' " +
                "        AND l.date_list = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT l.id, r.id as partner_id " +
                "FROM rnrc_ref_partner r " +
                "         JOIN legal_person l ON " +
                "        l.ogrn = r.ogrn " +
                "        AND l.list_name = '" + listName.replace("'", "''") + "' " +
                "        AND l.date_list = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT l.id, r.id as partner_id " +
                "FROM rnrc_ref_partner r " +
                "         JOIN legal_person l ON " +
                "        l.organization_name = r.fullname " +
                "        AND l.list_name = '" + listName.replace("'", "''") + "' " +
                "        AND l.date_list = '" + dateList.replace("'", "''") + "' ";
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Transactional
    public List<ResponseDTO> getCheckResponseForAllUsers(QlikViewRequest request, LocalDate checkDate) {
        String date = checkDate.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String dateNow = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        createViewForPhysicPerson(date, request.getRfmList().getListName());
        createViewForLegalPerson(date, request.getRfmList().getListName());
        responseRepository.insertResponseRecordsFromTable();
        List<ResponseEntity> matchingRecords;
        if (request.isAllPartners()) {
            matchingRecords = responseRepository.findAll();

        } else {
            matchingRecords = responseRepository.findAllByPartnerId(request.getPartnerId());
        }
        List<ResponseDTO> responseList = matchingRecords.stream().map(record -> {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setRequestId(request.getRequestId());
            responseDTO.setPartnerId(record.getPartnerId());
            responseDTO.setCheckObject(record.getCheckObject());
            responseDTO.setLinkedStructureOrder(record.getLinkedStructureOrder());
            responseDTO.setParticipantOrder(record.getParticipantOrder());
            responseDTO.setShortName(record.getShortName());
            responseDTO.setCheckDate(dateNow);
            responseDTO.setListDate(date);
            responseDTO.setListName(request.getRfmList().getListFullName());
            responseDTO.setCheckResult(record.getCheckResult() + date);
            responseDTO.setListId(record.getListId());
            return responseDTO;
        }).collect(Collectors.toList());
        responseRepository.cleanResultTable();
        responseRepository.cleanLegalPersonTable();
        responseRepository.cleanPhysicPersonTable();
        return responseList;
    }

}
