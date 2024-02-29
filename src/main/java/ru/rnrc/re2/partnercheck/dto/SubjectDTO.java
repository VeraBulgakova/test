package ru.rnrc.re2.partnercheck.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SubjectDTO {
    @XmlElement(name = "ИдСубъекта")
    private String subjectId;

    @XmlElement(name = "ТипСубъекта")
    private SubjectTypeDTO subjectTypeDTO;

    @XmlElement(name = "ФЛ")
    private PhysicalPersonDTO physicalPersonDTO;

    @XmlElements({
            @XmlElement(name = "ЮЛ", type = LegalEntityDTO.class),
            @XmlElement(name = "Орг", type = LegalEntityDTO.class)
    })
    private LegalEntityDTO legalEntityDTO;
}
