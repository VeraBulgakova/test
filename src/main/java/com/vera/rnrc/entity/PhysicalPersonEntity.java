package com.vera.rnrc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "physical_person")
public class PhysicalPersonEntity {

    @Id
    private String id;

    @Column(name = "datelist")
    private String dateList;

    @Column(name = "listname")
    private String listName;

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

}
