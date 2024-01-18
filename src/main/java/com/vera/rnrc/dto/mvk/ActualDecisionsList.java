package com.vera.rnrc.dto.mvk;

import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ActualDecisionsList {

    @XmlElement(name = "Решение")
    private List<DecisionDTO> decisions;
}