package com.vera.rnrc.dto.mvk;

import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class FLDTO {
    @XmlElement(name = "ФИО")
    private String fullName;

    @XmlElement(name = "ФИОЛат")
    private String fullNameLat;

    @XmlElement(name = "ДатаРождения")
    private String dateOfBirth;

    @XmlElement(name = "ГодРождения")
    private int yearOfBirth;

    @XmlElementWrapper(name = "СписокДокументов")
    @XmlElement(name = "Документ")
    private List<DocumentDTO> documents;

    @XmlElementWrapper(name = "СписокДрНаименований")
    @XmlElement(name = "ДрНаименование")
    private List<OtherNameDTO> otherNames;
}