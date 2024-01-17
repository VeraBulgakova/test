package com.vera.rnrc.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Address {
    @XmlElement(name = "ТипАдреса")
    private AddressType addressType;
    @XmlElement(name = "ТекстАдреса")
    private String addressText;
    @XmlElement(name = "Страна")
    private Country country;
    @XmlElement(name = "ОКАТО")
    private String okato;
    @XmlElement(name = "Регион")
    private String region;
    @XmlElement(name = "Район")
    private String district;
    @XmlElement(name = "Город")
    private String city;
    @XmlElement(name = "Улица")
    private String street;
    @XmlElement(name = "Дом")
    private String house;
    @XmlElement(name = "Помещение")
    private String room;
}
