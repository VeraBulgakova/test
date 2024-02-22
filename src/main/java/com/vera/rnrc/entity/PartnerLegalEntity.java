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
@Table(name = "rnrc_ref_partner")
@NoArgsConstructor
@AllArgsConstructor
public class PartnerLegalEntity {
    @Id
    private String id;

    @Column(name = "partnername")
    private String partnername;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "ogrn")
    private String ogrn;

    @Column(name = "inn")
    private String inn;

}