package ru.rnrc.re2.partnercheck.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "index_olr_partnerlinkedstructure")
@NoArgsConstructor
@AllArgsConstructor
public class LinkedPartner {
    @Id
    @Column(name = "pzinskey")
    protected String pzinskey;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_legal_id", referencedColumnName = "id", nullable = false)
    private Partner partner;

}
