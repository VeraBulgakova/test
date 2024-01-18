package com.vera.rnrc.dto.mvk;

import lombok.Data;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class OtherNameDTO {
    @XmlElement(name = "ФИО")
    private String fullName;

    @XmlElement(name = "ДатаРождения")
    private String dateOfBirth;

    @XmlElement(name = "ГодРождения")
    private int yearOfBirth;
}
