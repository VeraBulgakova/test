package com.vera.rnrc.dto;


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

@XmlAccessorType(XmlAccessType.FIELD)
@Data
class RFMList {
    @XmlElement(name = "terroristlist")
    private boolean terroristList;

    @XmlElement(name = "fromlist")
    private boolean fromList;

    @XmlElement(name = "mvklist")
    private boolean mvkList;

    @XmlElement(name = "romlist")
    private boolean romList;

}
