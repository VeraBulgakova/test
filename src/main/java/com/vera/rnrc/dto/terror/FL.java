package com.vera.rnrc.dto.terror;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class FL {
    @XmlElement(name = "ФИО")
    private String fullName;

    @XmlElement(name = "Фамилия")
    private String surname;

    @XmlElement(name = "Имя")
    private String name;

    @XmlElement(name = "Отчество")
    private String patronymic;

    @XmlElement(name = "ФИОЛат")
    private String fullNameLat;

    @XmlElement(name = "ДатаРождения")
    private String DateOfBirth;

    @XmlElement(name = "ГодРождения")
    private int yearOfBirth;

    @XmlElement(name = "МестоРождения")
    private String placeOfBirth;

    @XmlElement(name = "ИНН")
    private String INN;

    @XmlElement(name = "СНИЛС")
    private String snils;

    @XmlElementWrapper(name = "СписокДокументов")
    @XmlElement(name = "Документ")
    private List<Document> documentList;

    @XmlElementWrapper(name = "СписокДрНаименований")
    @XmlElement(name = "ДрНаименование")
    private List<OtherNames> listOfOtherNames;

    @XmlElementWrapper(name = "СписокГражданств")
    @XmlElement(name = "Гражданство")
    private List<Nationality> nationalityList;
}
