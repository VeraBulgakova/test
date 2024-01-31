package com.vera.rnrc.dto.mvk;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class FLDTO {
    @XmlElement(name = "ФИО")
    private String fullName;

    @XmlElement(name = "ФИОЛат")
    private String fullNameLat;

    @XmlElement(name = "Фамилия")
    private String surname;

    @XmlElement(name = "Имя")
    private String name;

    @XmlElement(name = "Отчество")
    private String patronymic;

    @XmlElement(name = "ДатаРождения")
    private String dateOfBirth;

    @XmlElement(name = "ГодРождения")
    private int yearOfBirth;

    @XmlElement(name = "МестоРождения")
    private String placeOfBirth;

    @XmlElement(name = "ИНН")
    private String inn;

    @XmlElementWrapper(name = "СписокДокументов")
    @XmlElement(name = "Документ")
    private List<DocumentDTO> documents;

    @XmlElementWrapper(name = "СписокДрНаименований")
    @XmlElement(name = "ДрНаименование")
    private List<OtherNameDTO> otherNames;
}