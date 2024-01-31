package com.vera.rnrc.service;

import com.vera.rnrc.dto.request.QlikViewRequest;
import com.vera.rnrc.dto.request.RFMList;
import com.vera.rnrc.dto.response.ResponseDTO;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ResponseService {

    private final LegalPersonRepository legalPersonRepository;
    private final PhysicalPersonRepository physicalPersonRepository;

    @Autowired
    public ResponseService(LegalPersonRepository legalPersonRepository, PhysicalPersonRepository physicalPersonRepository) {
        this.legalPersonRepository = legalPersonRepository;
        this.physicalPersonRepository = physicalPersonRepository;
    }

    public List<ResponseDTO> getCheckResponse(QlikViewRequest request) {

        List<PhysicalPersonEntity> matchingRecords = physicalPersonRepository.findMatchingRecords();
        List<LegalPersonEntity> responseList2 = legalPersonRepository.findByCustomCondition("10.01.2024", request.getRfmList().getListName());
//        List<LegalPersonEntity> matchingRecords = legalPersonRepository.findMatchingRecords();
        List<ResponseDTO> responseList = new ArrayList<>();
//
//        for (PhysicalPersonEntity record : matchingRecords) {
//            ResponseDTO responseDTO = new ResponseDTO();
//
//            responseDTO.setRequestId(Long.valueOf(record.getId()));
//            responseDTO.setPartnerId(null);
//            responseDTO.setCheckObject(null);
//            responseDTO.setLinkedStructureOrder(null);
//            responseDTO.setParticipantOrder(null);
//            responseDTO.setShortName(record.getFullName());
//            responseDTO.setCheckDate(record.getDateList());
//            responseDTO.setListDate(record.getDateList());
//            responseDTO.setListName(record.getListName());
//            responseDTO.setCheckResult(null);
//
//            responseList.add(responseDTO);
//        }
        for (LegalPersonEntity record : responseList2) {
            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRequestId(request.getRequestId());
            responseDTO.setPartnerId(Long.valueOf(record.getId()));
            responseDTO.setCheckObject(null); //СвязаннаяСтруктура_PG_Тип,
//            'Beneficiary',
//                    'BenefitHolder',
//                    'ManagementBody',
//                    'Representative'),
//
//            'Бенефициар',
//                    'Выгодоприобретатель',
//                    'Управляющий орган',
//                    'Представитель'
            responseDTO.setLinkedStructureOrder(null);
            responseDTO.setParticipantOrder(null);
            responseDTO.setShortName(record.getOrganizationName());
            responseDTO.setCheckDate(LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
            responseDTO.setListDate(record.getDateList());
            responseDTO.setListName(request.getRfmList().getListFullName());
            responseDTO.setCheckResult(null);

            responseList.add(responseDTO);
        }

        return responseList;
    }
//    public ResponseDTO getCheckResponse(QlikViewRequest request) {
//
//        ResponseDTO responseDTO = new ResponseDTO();
//
//        responseDTO.setRequestId(request.getRequestId());
//        responseDTO.setPartnerId(request.getPartnerId());
//        responseDTO.setCheckObject("checkObject");
//        responseDTO.setLinkedStructureOrder("linkedStructureOrder");
//        responseDTO.setParticipantOrder("participantOrder");
//        responseDTO.setShortName("shortName");
//        responseDTO.setCheckDate("checkDate");
//        responseDTO.setListDate("listDate");
//        responseDTO.setListName("listName");
//        responseDTO.setCheckResult("checkResult");
//
//        return responseDTO;
//    }
}
