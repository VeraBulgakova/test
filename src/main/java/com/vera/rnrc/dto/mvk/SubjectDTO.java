package com.vera.rnrc.dto.mvk;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class SubjectDTO {
    @XmlElement(name = "ИдСубъекта")
    private long subjectId;

    @XmlElement(name = "ТипСубъекта")
    private SubjectType subjectType;

    @XmlElement(name = "ФЛ")
    private FLDTO fl;

    @XmlElement(name = "РешениеПоСубъекту")
    private String decisionBySubject;
}