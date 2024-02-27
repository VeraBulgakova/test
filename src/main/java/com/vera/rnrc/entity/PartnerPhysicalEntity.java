package com.vera.rnrc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "index_olr_partnerlinkedstructure")
@NoArgsConstructor
@AllArgsConstructor
public class PartnerPhysicalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "pzinskey")
    private String pzinskey;

    @Column(name = "inn")
    private String inn;

    @Column(name = "docseries")
    private String docSeries;

    @Column(name = "docnumber")
    private String docNumber;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "middlename")
    private String middlename;

    @Column(name = "dateofbirth")
    private String dateOfBirth;

    @Column(name = "placeofbirth")
    private String placeOfBirth;

    @Column(name = "linkedstructuretype")
    private String linkedstructuretype;

    @Column(name = "managementbodyordernumber")
    private String managementbodyordernumber;

    @Column(name = "participantordernumber")
    private String participantordernumber;


    public String convertTypeToStructureKey() {

        switch (linkedstructuretype) {
            case "Beneficiary":
                return "BENEFICIARYPARTNERLINKEDSTRUCTURE";
            case "BenefitHolder":
                return "PERSONBENEFITHOLDERPARTNERLINKEDSTRUCTURE";
            case "ManagementBody":
                return "MANAGEMENTBODYPERSON";
            case "Representative":
                return "REPRESENTATIVEPARTNERLINKEDSTRUCTURE";
            default:
                return "";
        }
    }
}