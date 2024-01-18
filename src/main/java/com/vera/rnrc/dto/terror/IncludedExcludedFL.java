package com.vera.rnrc.dto.terror;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class IncludedExcludedFL {
    @XmlElement(name = "ФИО")
    private String fullName;

    @XmlElement(name = "Фамилия")
    private String surname;

    @XmlElement(name = "Имя")
    private String name;

    @XmlElement(name = "Отчество")
    private String petronymic;

    @XmlElement(name = "ДатаРождения")
    private String dateOfBirth;

    @XmlElement(name = "МестоРождения")
    private String placeOfBirth;

    @XmlElement(name = "ИНН")
    private String INN;

    @XmlElement(name = "СНИЛС")
    private String snils;

    @XmlElementWrapper(name = "СписокДокументов")
    @XmlElement(name = "Документ")
    private List<Document> documentList;

}
