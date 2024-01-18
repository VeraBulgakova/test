package com.vera.rnrc.dto.romu;
import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class EntityDTO {

    @XmlElement(name = "DATAID")
    private Long dataId;

    @XmlElement(name = "VERSIONNUM")
    private Integer versionNum;

    @XmlElement(name = "FIRST_NAME")
    private String firstName;

    @XmlElement(name = "UN_LIST_TYPE")
    private String unListType;

    @XmlElement(name = "REFERENCE_NUMBER")
    private String referenceNumber;

    @XmlElement(name = "LISTED_ON")
    private String listedOn;

    @XmlElement(name = "COMMENTS1")
    private String comments1;

    @XmlElementWrapper(name = "LIST_TYPE")
    @XmlElement(name = "VALUE")
    private List<String> listTypes;

    @XmlElementWrapper(name = "LAST_DAY_UPDATED")
    @XmlElement(name = "VALUE")
    private List<String> lastDayUpdated;

    @XmlElementWrapper(name = "ENTITY_ALIAS")
    @XmlElement(name = "ENTITY_ALIAS")
    private List<EntityAliasDTO> entityAliases;

    @XmlElementWrapper(name = "ENTITY_ADDRESS")
    @XmlElement(name = "ENTITY_ADDRESS")
    private List<EntityAddressDTO> entityAddresses;
}

