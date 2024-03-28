package ru.rnrc.re2.partnercheck.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PartnerDTO {
    @Schema(type = "string", example = "МЕСТНАЯ РЕЛИГИОЗНАЯ ОРГАНИЗАЦИЯ СВИДЕТЕЛЕЙ ИЕГОВЫ 'САРАНСК'")
    private String partnername;
    @Schema(type = "string", example = "МЕСТНАЯ РЕЛИГИОЗНАЯ ОРГАНИЗАЦИЯ СВИДЕТЕЛЕЙ ИЕГОВЫ 'САРАНСК'")
    private String fullname;
    @Schema(type = "string", example = "1031322002051")
    private String ogrn;
    @Schema(type = "string", example = "1327153416")
    private String inn;

    private List<LinkedPartnerDTO> beneficiary;

    private List<LinkedPartnerDTO> benefitHolder;

    private List<LinkedPartnerDTO> representative;

    private List<LinkedPartnerDTO> managementBodyList;
}
