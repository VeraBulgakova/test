package com.vera.rnrc.dto.mvk;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class LegalEntity {
    @XmlElement(name = "Наименование")
    private String name;

    @XmlElement(name = "НаименованиеЛат")
    private String nameLat;

    @XmlElementWrapper(name = "СписокДрНаименований")
    @XmlElement(name = "ДрНаименование")
    private List<OtherName> otherNames;

    @XmlElement(name = "ИНН")
    private String inn;

    @XmlElement(name = "ОГРН")
    private String ogrn;

}
