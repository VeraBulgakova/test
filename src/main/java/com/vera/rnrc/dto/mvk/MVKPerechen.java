package com.vera.rnrc.dto.mvk;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "СписокРешений")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MVKPerechen {

    @XmlElement(name = "ВерсияФормата")
    private String versionFormat;

    @XmlElement(name = "ДатаСписка")
    private String dateList;

    @XmlElement(name = "ДатаПредыдущегоСписка")
    private String datePreviousList;

    @XmlElement(name = "СписокАктуальныхРешений")
    private ActualDecisionsList actualDecisionsList;
}
