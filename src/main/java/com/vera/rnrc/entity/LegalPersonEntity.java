package com.vera.rnrc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "legal_person")
@NoArgsConstructor
@AllArgsConstructor
public class LegalPersonEntity {
    @Id
    private String id;

    @Column(name = "datelist")
    private String dateList;

    @Column(name = "listname")
    private String listName;

    @Column(name = "ogrn")
    private String ogrn;

    @Column(name = "inn")
    private String inn;

    @Column(name = "fullname")
    private String fullname;

}
