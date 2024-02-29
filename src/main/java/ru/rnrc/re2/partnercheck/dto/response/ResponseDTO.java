package ru.rnrc.re2.partnercheck.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private String requestId;
    private String partnerId;
    private String checkObject;
    private String linkedStructureOrder;
    private String participantOrder;
    private String shortName;
    private String checkDate;
    private String listDate;
    private String listName;
    private String checkResult;
    private String listId;
}