package ru.rnrc.re2.partnercheck.dto.romu;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class IndividualDTO {

    @XmlElement(name = "DATAID")
    private Long dataId;

    @XmlElement(name = "VERSIONNUM")
    private Integer versionNum;

    @XmlElement(name = "FIRST_NAME")
    private String firstName;

    @XmlElement(name = "SECOND_NAME")
    private String secondName;

    @XmlElement(name = "THIRD_NAME")
    private String thirdName;

    @XmlElement(name = "FOURTH_NAME")
    private String fourthName;

    @XmlElement(name = "UN_LIST_TYPE")
    private String unListType;

    @XmlElement(name = "REFERENCE_NUMBER")
    private String referenceNumber;

    @XmlElement(name = "LISTED_ON")
    private String listedOn;

    @XmlElement(name = "NAME_ORIGINAL_SCRIPT")
    private String nameOriginalScript;

    @XmlElement(name = "COMMENTS1")
    private String comments1;

    @XmlElementWrapper(name = "TITLE")
    @XmlElement(name = "VALUE")
    private List<String> titles;

    @XmlElementWrapper(name = "DESIGNATION")
    @XmlElement(name = "VALUE")
    private List<String> designations;

    @XmlElementWrapper(name = "NATIONALITY")
    @XmlElement(name = "VALUE")
    private List<String> nationalities;

    @XmlElementWrapper(name = "LIST_TYPE")
    @XmlElement(name = "VALUE")
    private List<String> listTypes;

    @XmlElementWrapper(name = "LAST_DAY_UPDATED")
    @XmlElement(name = "VALUE")
    private List<String> lastDayUpdated;

    @XmlElementWrapper(name = "INDIVIDUAL_ALIAS")
    @XmlElement(name = "INDIVIDUAL_ALIAS")
    private List<IndividualAliasDTO> aliases;

    @XmlElement(name = "INDIVIDUAL_ADDRESS")
    private IndividualAddressDTO address;

    @XmlElement(name = "INDIVIDUAL_DATE_OF_BIRTH")
    private IndividualDateOfBirthDTO dateOfBirth;

    @XmlElement(name = "INDIVIDUAL_PLACE_OF_BIRTH")
    private IndividualPlaceOfBirthDTO placeOfBirth;

    @XmlElement(name = "INDIVIDUAL_DOCUMENT")
    private IndividualDocumentDTO document;

    public String getFullName() {
        return Stream.of(firstName, secondName, thirdName, fourthName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }
}
