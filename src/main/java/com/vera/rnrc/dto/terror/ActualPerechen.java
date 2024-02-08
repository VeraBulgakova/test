package com.vera.rnrc.dto.terror;

import com.vera.rnrc.dto.Subject;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ActualPerechen {
    @XmlElement(name = "Субъект")
    private List<Subject> subjects;

}