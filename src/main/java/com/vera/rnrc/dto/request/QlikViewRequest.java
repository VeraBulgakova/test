package com.vera.rnrc.dto.request;


import lombok.Data;
import javax.xml.bind.annotation.*;
@XmlRootElement(name = "QlikViewRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class QlikViewRequest {
    @XmlElement(name = "request_id")
    private long requestId;

    @XmlElement(name = "allpartners")
    private boolean allPartners;

    @XmlElement(name = "partner_id")
    private long partnerId;

    @XmlElement(name = "rfmlist")
    private RFMList rfmList;

}

