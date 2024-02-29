package ru.rnrc.re2.partnercheck.dto.terror;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Перечень")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class TERRORPerechenDTO {
    @XmlElement(name = "ВерсияФормата")
    private String version;

    @XmlElement(name = "ДатаПеречня")
    private String dateList;

    @XmlElement(name = "ДатаПредыдущегоПеречня")
    private String datePreviousList;

    @XmlElement(name = "АктуальныйПеречень")
    private ActualPerechenDTO actualPerechenDTO;

}
