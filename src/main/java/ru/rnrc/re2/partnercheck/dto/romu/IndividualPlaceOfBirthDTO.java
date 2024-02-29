package ru.rnrc.re2.partnercheck.dto.romu;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class IndividualPlaceOfBirthDTO {
    @XmlElement(name = "CITY")
    private String city;

    @XmlElement(name = "STATE_PROVINCE")
    private String stateProvince;

    @XmlElement(name = "COUNTRY")
    private String country;
}
