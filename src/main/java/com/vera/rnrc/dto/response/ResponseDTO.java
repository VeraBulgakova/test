package com.vera.rnrc.dto.response;

import lombok.Data;

@Data
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