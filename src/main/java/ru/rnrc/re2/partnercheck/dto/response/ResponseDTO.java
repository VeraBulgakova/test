package ru.rnrc.re2.partnercheck.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    @Schema(type = "string", example = "1705499801")
    private String requestId;
    @Schema(type = "string", example = "1510432696")
    private String partnerId;
    @Schema(type = "string", example = "Представитель")
    private String checkObject;
    @Schema(type = "string", example = "1")
    private String linkedStructureOrder;
    @Schema(type = "string", example = "1")
    private String participantOrder;
    @Schema(type = "string", example = "Абрамов Андрей Николаевич")
    private String shortName;
    @Schema(type = "string", example = "19.03.2024 14:09:10")
    private String checkDate;
    @Schema(type = "string", example = "19.03.2024")
    private String listDate;
    @Schema(type = "string", example = "Перечень лиц, причастных к экстремизму, терроризму")
    private String listName;
    @Schema(type = "string", example = "Не идентифицирован по перечню от 10.01.2024")
    private String checkResult;
    @Schema(type = "string", example = "null")
    private String listId;
}