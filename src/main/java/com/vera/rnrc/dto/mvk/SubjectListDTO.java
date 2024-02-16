package com.vera.rnrc.dto.mvk;

import com.vera.rnrc.dto.SubjectDTO;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class SubjectListDTO {
    @XmlElement(name = "Субъект")
    private List<SubjectDTO> subjectsDTO;
}