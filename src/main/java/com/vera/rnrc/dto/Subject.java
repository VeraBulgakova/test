package com.vera.rnrc.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Subject {
    @XmlElement(name = "ИдСубъекта")
    private long subjectId;

    @XmlElement(name = "ТипСубъекта")
    private SubjectType subjectType;

    @XmlElement(name = "История")
    private History history;

    @XmlElement(name = "ФЛ")
    private FL fl;

    @XmlElementWrapper(name = "СписокАдресов")
    @XmlElement(name = "Адрес")
    private List<Address> listOfAddress;

    @XmlElement(name = "Террорист")
    private int terrorist;

}