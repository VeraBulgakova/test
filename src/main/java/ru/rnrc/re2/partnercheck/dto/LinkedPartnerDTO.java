package ru.rnrc.re2.partnercheck.dto;

import lombok.Data;

@Data
public class LinkedPartnerDTO {

    private String inn;
    private String docSeries;
    private String docNumber;
    private String fullname;
    private String lastname;
    private String firstname;
    private String countryName;
    private String middlename;
    private String dateOfBirth;
    private String placeOfBirth;
    private String linkedstructuretype;
    private int benefitPersonType;
    private int managementBodyType;
}
