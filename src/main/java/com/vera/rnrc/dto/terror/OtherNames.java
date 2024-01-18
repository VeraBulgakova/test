package com.vera.rnrc.dto.terror;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class OtherNames {

    @XmlElement(name = "ФИО")
    private String fullName;

    @XmlElement(name = "ДатаРождения")
    private String dateOfBirth;

    @XmlElement(name = "ГодРождения")
    private int yearOfBirth;

}
