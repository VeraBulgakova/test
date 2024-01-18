package com.vera.rnrc.dto.terror;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class History {
    @XmlElement(name = "ДатаВключения")
    private String DateOfInclusion;
}
