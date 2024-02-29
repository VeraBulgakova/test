package ru.rnrc.re2.partnercheck.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SubjectTypeDTO {
    @XmlElement(name = "Идентификатор")
    private long SubjectTypeId;

    @XmlElement(name = "Наименование")
    private String name;
}
