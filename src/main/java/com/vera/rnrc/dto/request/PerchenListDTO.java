package com.vera.rnrc.dto.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class PerchenListDTO {

    @XmlElement(name = "terroristlist")
    private boolean terroristList;

    @XmlElement(name = "fromlist")
    private boolean fromList;

    @XmlElement(name = "mvklist")
    private boolean mvkList;

    @XmlElement(name = "romlist")
    private boolean romList;

    public String getListName() {
        if (terroristList) {
            return "Террор";
        } else if (fromList) {
            return "РОМУ";
        } else if (mvkList) {
            return "МВК";
        } else if (romList) {
            return "РОМУ";
        }
        return null;
    }

    public String getListFullName() {
        if (terroristList) {
            return "Перечень лиц, причастных к экстремизму, терроризму";
        } else if (fromList) {
            return "Перечень лиц, причастных к терроризму и РОМУ";
        } else if (romList) {
            return "Перечень лиц, причастных к терроризму и РОМУ";
        } else if (mvkList) {
            return "Перечень МВК";
        }
        return null;
    }

}
