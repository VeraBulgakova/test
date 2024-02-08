package com.vera.rnrc.dto.mvk;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class DecisionDTO {

    @XmlElement(name = "НомерРешения")
    private String decisionNumber;

    @XmlElement(name = "ДатаРешения")
    private String decisionDate;

    @XmlElement(name = "Орган")
    private String organ;

    @XmlElement(name = "СписокСубъектов")
    private SubjectList subjectList;
}