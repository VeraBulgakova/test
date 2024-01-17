package com.vera.rnrc.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Document {
    @XmlElement(name = "ТипДокумента")
    private DocumentType documentType;

    @XmlElement(name = "Серия")
    private String series;

    @XmlElement(name = "Номер")
    private String number;

    @XmlElement(name = "ОрганВыдачи")
    private String issuingAuthority;

    @XmlElement(name = "ДатаВыдачи")
    private String dateOfIssue;
}
