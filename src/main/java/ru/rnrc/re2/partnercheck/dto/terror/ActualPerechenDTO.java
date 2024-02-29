package ru.rnrc.re2.partnercheck.dto.terror;

import lombok.Data;
import ru.rnrc.re2.partnercheck.dto.SubjectDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ActualPerechenDTO {
    @XmlElement(name = "Субъект")
    private List<SubjectDTO> subjectsDTO;

}