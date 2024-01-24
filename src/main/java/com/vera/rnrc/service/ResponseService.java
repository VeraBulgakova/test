package com.vera.rnrc.service;

import com.vera.rnrc.dto.QlikViewRequest;
import com.vera.rnrc.dto.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {
    public ResponseDTO getCheckResponse(QlikViewRequest request) {
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setRequestId(request.getRequestId());
        responseDTO.setPartnerId(request.getPartnerId());
        responseDTO.setCheckObject("checkObject");
        responseDTO.setLinkedStructureOrder("linkedStructureOrder");
        responseDTO.setParticipantOrder("participantOrder");
        responseDTO.setShortName("shortName");
        responseDTO.setCheckDate("checkDate");
        responseDTO.setListDate("listDate");
        responseDTO.setListName("listName");
        responseDTO.setCheckResult("checkResult");

        return responseDTO;
    }
}
