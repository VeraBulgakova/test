package ru.rnrc.re2.partnercheck.dto.mvk;

import lombok.Data;
import ru.rnrc.re2.partnercheck.dto.SubjectDTO;

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
