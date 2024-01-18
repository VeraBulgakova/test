package com.vera.rnrc.dto.romu;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class IndividualDocumentDTO {

    @XmlElement(name = "TYPE_OF_DOCUMENT")
    private String typeOfDocument;

    @XmlElement(name = "NUMBER")
    private String number;

    @XmlElement(name = "ISSUING_COUNTRY")
    private String issuingCountry;

    @XmlElement(name = "NOTE")
    private String note;
}