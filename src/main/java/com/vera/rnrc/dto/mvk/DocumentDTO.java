package com.vera.rnrc.dto.mvk;

import lombok.Data;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class DocumentDTO {
    @XmlElement(name = "ТипДокумента")
    private DocumentType documentType;

    @XmlElement(name = "Серия")
    private String series;

    @XmlElement(name = "Номер")
    private String number;
}