package com.vera.rnrc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "legal_person")
public class LegalPersonEntity {
    @Id
    private Long id;

    @Column(name = "date_list")
    private String dateList;

    @Column(name = "list_name")
    private String listName;

    @Column(name = "ogrn")
    private String ogrn;

    @Column(name = "inn")
    private String inn;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "resident_sign")
    private String residentSign;
}
