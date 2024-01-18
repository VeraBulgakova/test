package com.vera.rnrc.dto.mvk;
import lombok.Data;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "СписокРешений")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MVKDecisionListDTO {

    @XmlElement(name = "ВерсияФормата")
    private String versionFormat;

    @XmlElement(name = "ДатаСписка")
    private String dateList;

    @XmlElement(name = "ДатаПредыдущегоСписка")
    private String datePreviousList;

    @XmlElement(name = "СписокАктуальныхРешений")
    private ActualDecisionsList actualDecisionsList;
}
