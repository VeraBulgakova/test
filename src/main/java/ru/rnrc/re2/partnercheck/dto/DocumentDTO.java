package ru.rnrc.re2.partnercheck.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class DocumentDTO {
    @XmlElement(name = "Серия")
    private String series;

    @XmlElement(name = "Номер")
    private String number;

    @XmlElement(name = "ОрганВыдачи")
    private String issuingAuthority;

    @XmlElement(name = "ДатаВыдачи")
    private String dateOfIssue;
}
