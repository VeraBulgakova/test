package ru.rnrc.re2.partnercheck.mapper;

import org.springframework.stereotype.Component;
import ru.rnrc.re2.partnercheck.dto.request.RequestDTO;
import ru.rnrc.re2.partnercheck.dto.response.ResponseDTO;
import ru.rnrc.re2.partnercheck.entity.Response;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResponseMapper {
    public List<ResponseDTO> toResponseDTOList(List<Response> matchingRecords, RequestDTO request, String dateNow, String date) {
        return matchingRecords.stream().map(record -> new ResponseDTO(
                request.getRequestId(),
                record.getPartnerId(),
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
