package com.vera.rnrc.dto.terror;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class AddressType {
    @XmlElement(name = "Идентификатор")
    private long AddressTypeId;

    @XmlElement(name = "Наименование")
    private String name;
}
