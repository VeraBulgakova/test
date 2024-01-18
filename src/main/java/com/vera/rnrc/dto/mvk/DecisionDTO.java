package com.vera.rnrc.dto.mvk;

import lombok.Data;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class DecisionDTO {

    @XmlElement(name = "ТипРешения")
    private DecisionType decisionType;

    @XmlElement(name = "НомерРешения")
    private String decisionNumber;

    @XmlElement(name = "ДатаРешения")
    private String decisionDate;

    @XmlElement(name = "Орган")
    private String organ;

    @XmlElement(name = "ВидРешения")
    private DecisionKind decisionKind;

    @XmlElement(name = "СписокСубъектов")
    private SubjectList subjectList;
}