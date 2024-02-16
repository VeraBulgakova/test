package com.vera.rnrc.service;

import com.vera.rnrc.dto.request.RequestDTO;
import com.vera.rnrc.dto.response.ResponseDTO;
import com.vera.rnrc.entity.ResponseEntity;
import com.vera.rnrc.repository.ResponseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseServiceImpl implements ResponseService {

    private final ResponseRepository responseRepository;
    private static final Logger logger = LoggerFactory.getLogger(ResponseServiceImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ResponseServiceImpl(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    @Override
    @Transactional
    public void createViewForPhysicPerson(@Param("dateList") String dateList, @Param("listName") String listName) {
        String sql = "CREATE or replace VIEW viewPhysical AS " +
                "SELECT p.id, iop.pzinskey as partner_id " +
                "FROM index_olr_partnerlinkedstructure iop " +
                "         JOIN physical_person p ON " +
                "    p.inn = iop.inn " +
                "        AND p.listname = '" + listName.replace("'", "''") + "' " +
                "        AND p.datelist = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT p.id, iop.pzinskey as partner_id " +
                "FROM index_olr_partnerlinkedstructure iop " +
                "         JOIN physical_person p ON " +
                "    p.docseries = iop.docseries AND p.docnumber = iop.docnumber " +
                "        AND p.listname = '" + listName.replace("'", "''") + "' " +
                "        AND p.datelist = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT p.id, iop.pzinskey as partner_id " +
                "FROM index_olr_partnerlinkedstructure iop " +
                "         JOIN physical_person p ON " +
                "    p.dateofbirth = iop.dateofbirth " +
                "        AND LOWER(p.lastname) = LOWER(iop.lastname) AND LOWER(p.firstname) = LOWER(iop.firstname) " +
                "        AND LOWER(p.middlename) = LOWER(iop.middlename) " +
                "        AND p.listname = '" + listName.replace("'", "''") + "' " +
                "        AND p.datelist = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT p.id, iop.pzinskey as partner_id " +
                "FROM index_olr_partnerlinkedstructure iop " +
                "         JOIN physical_person p ON " +
                "    p.dateofbirth = iop.dateofbirth " +
                "        AND LOWER(p.fullname) = LOWER(iop.fullname) " +
                "        AND p.listname = '" + listName.replace("'", "''") + "' " +
                "        AND p.datelist = '" + dateList.replace("'", "''") + "' ;";
        entityManager.createNativeQuery(sql).executeUpdate();

        logger.info("View for physical persons created successfully");
    }

    @Override
    @Transactional
    public void createViewForLegalPerson(@Param("dateList") String dateList, @Param("listName") String listName) {
        String sql = "CREATE or replace VIEW viewLegal AS " +
                "SELECT l.id, r.id as partner_id " +
                "FROM rnrc_ref_partner r " +
                "         JOIN legal_person l ON " +
                "        l.inn = r.inn " +
                "        AND l.listname = '" + listName.replace("'", "''") + "' " +
                "        AND l.datelist = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT l.id, r.id as partner_id " +
                "FROM rnrc_ref_partner r " +
                "         JOIN legal_person l ON " +
                "        l.ogrn = r.ogrn " +
                "        AND l.listname = '" + listName.replace("'", "''") + "' " +
                "        AND l.datelist = '" + dateList.replace("'", "''") + "' " +
                "union " +
                "SELECT l.id, r.id as partner_id " +
                "FROM rnrc_ref_partner r " +
                "         JOIN legal_person l ON " +
                "        l.fullname = r.fullname " +
                "        AND l.listname = '" + listName.replace("'", "''") + "' " +
                "        AND l.datelist = '" + dateList.replace("'", "''") + "' ";
        entityManager.createNativeQuery(sql).executeUpdate();

        logger.info("View for legal persons created successfully");
    }

    @Override
    @Transactional
    public List<ResponseDTO> getCheckResponseForPartners(RequestDTO request, LocalDate checkDate) {
        logger.info("Starting getCheckResponseForPartners method...");

        String date = checkDate.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String dateNow = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

        logger.info("Creating views for physical and legal persons...");
        createViewForPhysicPerson(date, request.getPerchenListDTO().getListName());
        createViewForLegalPerson(date, request.getPerchenListDTO().getListName());

        logger.info("Inserting response records from tables...");
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
            responseDTO.setListName(request.getPerchenListDTO().getListFullName());
            responseDTO.setCheckResult(record.getCheckResult() + date);
            responseDTO.setListId(record.getListId());
            return responseDTO;
        }).collect(Collectors.toList());

        logger.info("Cleaning result, legal person, and physical person tables...");
        responseRepository.cleanResultTable();
        responseRepository.cleanLegalPersonTable();
        responseRepository.cleanPhysicPersonTable();

        logger.info("getCheckResponseForPartners method completed successfully");
        return responseList;
    }

}
