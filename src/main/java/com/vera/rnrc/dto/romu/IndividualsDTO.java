package com.vera.rnrc.dto.romu;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class IndividualsDTO {

    @XmlElement(name = "INDIVIDUAL")
    private List<IndividualDTO> individual;
}
