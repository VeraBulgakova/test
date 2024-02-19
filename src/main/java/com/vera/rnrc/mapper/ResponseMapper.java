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
        return matchingRecords.stream().map(record -> new ResponseDTO(
                request.getPartnerId(),
                request.getPartnerId(),
                record.getCheckObject(),
                record.getLinkedStructureOrder(),
                record.getParticipantOrder(),
                record.getShortName(),
                dateNow,
                date,
                request.getPerchenListDTO().getListFullName(),
                record.getCheckResult() + date,
                record.getListId())).collect(Collectors.toList());
    }
}
