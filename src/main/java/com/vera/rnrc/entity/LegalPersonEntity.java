package com.vera.rnrc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class LegalPersonEntity {
    @Id
    private Long id;

    @Column(name = "date_list")
    private String dateList;

    @Column(name = "list_name")
    private String listName;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "ogrn")
    private String ogrn;

    @Column(name = "inn")
    private String inn;

    @Column(name = "organization_name")
    private String organizationName;
}
