package com.vera.rnrc.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Subject {
    @XmlElement(name = "ИдСубъекта")
    private long subjectId;

    @XmlElement(name = "ТипСубъекта")
    private SubjectType subjectType;

    @XmlElement(name = "ФЛ")
    private PhysicalPerson physicalPerson;

    @XmlElements({
            @XmlElement(name = "ЮЛ", type = LegalEntity.class),
            @XmlElement(name = "Орг", type = LegalEntity.class)
    })
    private LegalEntity legalEntity;
}
