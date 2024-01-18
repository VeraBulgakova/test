package com.vera.rnrc.dto.romu;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class EntityAddressDTO {
    @XmlElement(name = "STATE_PROVINCE")
    private String stateProvince;

    @XmlElement(name = "CITY")
    private String city;

    @XmlElement(name = "COUNTRY")
    private String country;

    @XmlElement(name = "NOTE")
    private String note;
}