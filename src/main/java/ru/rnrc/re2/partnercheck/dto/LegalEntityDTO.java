package ru.rnrc.re2.partnercheck.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class LegalEntityDTO {
    @XmlElement(name = "Наименование")
    private String fullname;
    @XmlElement(name = "НаименованиеЛат")
    private String fullnameLat;
    @XmlElement(name = "ИНН")
    private String inn;
    @XmlElement(name = "ОГРН")
    private String ogrn;
}
