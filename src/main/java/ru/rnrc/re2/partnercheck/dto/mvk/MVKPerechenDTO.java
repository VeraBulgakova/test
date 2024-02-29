package ru.rnrc.re2.partnercheck.dto.mvk;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "СписокРешений")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MVKPerechenDTO {

    @XmlElement(name = "ВерсияФормата")
    private String versionFormat;

    @XmlElement(name = "ДатаСписка")
    private String dateList;

    @XmlElement(name = "ДатаПредыдущегоСписка")
    private String datePreviousList;

    @XmlElement(name = "СписокАктуальныхРешений")
    private ActualDecisionsListDTO actualDecisionsListDTO;
}
