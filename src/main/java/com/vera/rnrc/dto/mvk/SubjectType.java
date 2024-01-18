package com.vera.rnrc.dto.mvk;

import lombok.Data;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class SubjectType {
    @XmlElement(name = "Идентификатор")
    private int subjectTypeId;

    @XmlElement(name = "Наименование")
    private String name;
}