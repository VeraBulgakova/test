package ru.rnrc.re2.partnercheck.dto.romu;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "CONSOLIDATED_LIST")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ROMUPerechenDTO {

    @XmlElement(name = "INDIVIDUALS")
    private IndividualsDTO individualsDTO;

    @XmlElementWrapper(name = "ENTITIES")
    @XmlElement(name = "ENTITY")
    private List<EntityDTO> entities;
}