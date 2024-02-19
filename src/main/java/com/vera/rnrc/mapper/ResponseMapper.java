package com.vera.rnrc.mapper;

import com.vera.rnrc.dto.request.RequestDTO;
import com.vera.rnrc.dto.response.ResponseDTO;
import com.vera.rnrc.entity.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResponseMapper {
    public List<ResponseDTO> toResponseDTOList(List<ResponseEntity> matchingRecords, RequestDTO request, String dateNow, String date) {
        return matchingRecords.stream().map(record -> {
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
    }
}
