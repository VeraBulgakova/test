package ru.rnrc.re2.partnercheck.dto.romu;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class IndividualAliasDTO {

    @XmlElement(name = "QUALITY")
    private String quality;

    @XmlElement(name = "ALIAS_NAME")
    private String aliasName;
}
