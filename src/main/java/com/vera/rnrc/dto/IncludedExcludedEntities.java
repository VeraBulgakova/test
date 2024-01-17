package com.vera.rnrc.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class IncludedExcludedEntities {
    @XmlElement(name = "ИдСубъекта")
    private long SybjectId;

    @XmlElement(name = "ТипВклИсклСубъекта")
    private TypeIncludedExcludedEntities typeIncludedExcludedEntities;

    @XmlElement(name = "ВклИсклФЛ")
    private IncludedExcludedFL IncludedExcludedFL;
}
