package com.vera.rnrc.dto;

import lombok.Data;

@Data
public class ResponseDTO {
    private Long requestId;
    private Long partnerId;
    private String checkObject;
    private String linkedStructureOrder;
    private String participantOrder;
    private String shortName;
    private String checkDate;
    private String listDate;
    private String listName;
    private String checkResult;
    private Long listId;
}