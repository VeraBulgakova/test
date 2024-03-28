package ru.rnrc.re2.partnercheck.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkedPartnerDTO {
    @Schema(type = "string", example = "911015567638")
    private String inn;
    @Schema(type = "string", example = "3917")
    private String docSeries;
    @Schema(type = "string", example = "306932")
    private String docNumber;
    @Schema(type = "string", example = "Зиза Богдан Сергеевич")
    private String fullname;
    @Schema(type = "string", example = "Зиза")
    private String lastname;
    @Schema(type = "string", example = "Богдан")
    private String firstname;
    @Schema(type = "string", example = "Сергеевич")
    private String middlename;
    @Schema(type = "string", example = "23.11.1994")
    private String dateOfBirth;
    @Schema(type = "string", example = "Москва")
    private String placeOfBirth;
    @Schema(type = "string", example = "ManagementBody")
    private String linkedstructuretype;
    @Schema(type = "string", example = "0")
    private int benefitPersonType;
    @Schema(type = "string", example = "1")
    private int managementBodyType;
}
