package ru.rnrc.re2.partnercheck.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class PhysicalPersonDTO {
    @XmlElement(name = "ФИО")
    private String fullName;

    @XmlElement(name = "ФИОЛат")
    private String fullNameLat;

    @XmlElement(name = "Фамилия")
    private String lastname;

    @XmlElement(name = "Имя")
    private String firstname;

    @XmlElement(name = "Отчество")
    private String middlename;

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
    private List<DocumentDTO> documentDTOList;
}
