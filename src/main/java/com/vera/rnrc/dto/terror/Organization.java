package com.vera.rnrc.dto.terror;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Organization {
    @XmlElement(name = "Наименование")
    private String organizationName;
    @XmlElement(name = "НаименованиеЛат")
    private String organizationNameLat;
    @XmlElement(name = "ИНН")
    private String inn;
    @XmlElement(name = "ОГРН")
    private String ogrn;
}
