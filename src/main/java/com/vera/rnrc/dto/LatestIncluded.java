package com.vera.rnrc.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class LatestIncluded {
    @XmlElement(name = "ВклИсклСубъект")
    private List<IncludedExcludedEntities> includedExcludedEntities;

}
