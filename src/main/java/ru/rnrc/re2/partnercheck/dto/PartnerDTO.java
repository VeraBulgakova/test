package ru.rnrc.re2.partnercheck.dto;

import lombok.Data;

import java.util.List;

@Data
public class PartnerDTO {
    private String partnername;

    private String fullname;

    private String ogrn;

    private String inn;

    private List<LinkedPartnerDTO> beneficiary;

    private List<LinkedPartnerDTO> benefitHolder;

    private List<LinkedPartnerDTO> representative;

    private List<LinkedPartnerDTO> managementBodyList;
}
