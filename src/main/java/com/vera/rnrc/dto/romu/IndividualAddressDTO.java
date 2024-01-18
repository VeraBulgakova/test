package com.vera.rnrc.dto.romu;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class IndividualAddressDTO {
    @XmlElement(name = "COUNTRY")
    private String country;
}
