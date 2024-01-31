package com.vera.rnrc.dto.romu;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "CONSOLIDATED_LIST")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ConsolidatedListDTO {

    @XmlElement(name = "INDIVIDUALS")
    private Individuals individuals;

    @XmlElementWrapper(name = "ENTITIES")
    @XmlElement(name = "ENTITY")
    private List<EntityDTO> entities;
}