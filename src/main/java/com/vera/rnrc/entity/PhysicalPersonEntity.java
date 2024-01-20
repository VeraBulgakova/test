package com.vera.rnrc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class PhysicalPersonEntity {

    @Id
    private Long id;

    @Column(name = "date_list")
    private Date dateList;

    @Column(name = "list_name")
    private String listName;

    @Column(name = "inn")
    private String inn;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "resident_sign")
    private String residentSign;

}
