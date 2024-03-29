package ru.rnrc.re2.partnercheck.dto.request;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "QlikViewRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class RequestDTO {
    @XmlElement(name = "request_id")
    private String requestId;

    @XmlElement(name = "allpartners")
    private boolean allPartners;

    @XmlElement(name = "partner_id")
    private String partnerId;

    @XmlElement(name = "rfmlist")
    private PerchenListDTO perchenListDTO;

}

