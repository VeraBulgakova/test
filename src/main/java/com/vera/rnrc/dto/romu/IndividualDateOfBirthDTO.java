package com.vera.rnrc.dto.romu;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class IndividualDateOfBirthDTO {
    @XmlElement(name = "TYPE_OF_DATE")
    private String typeOfDate;

    @XmlElement(name = "YEAR")
    private Integer year;
}
