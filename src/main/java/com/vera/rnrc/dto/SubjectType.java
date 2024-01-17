package com.vera.rnrc.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SubjectType {
    @XmlElement(name = "Идентификатор")
    private long SubjectTypeId;

    @XmlElement(name = "Наименование")
    private String name;
}
