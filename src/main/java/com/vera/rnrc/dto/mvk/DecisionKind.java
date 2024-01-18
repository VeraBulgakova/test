package com.vera.rnrc.dto.mvk;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)

public class DecisionKind {
    @XmlElement(name = "Идентификатор")
    private int decisionKindId;

    @XmlElement(name = "Наименование")
    private String name;
}
