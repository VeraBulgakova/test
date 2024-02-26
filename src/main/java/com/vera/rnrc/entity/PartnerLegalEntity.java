package com.vera.rnrc.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "partner_id")
    private String partnerId;

    @Column(name = "partnername")
    private String partnername;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "ogrn")
    private String ogrn;

    @Column(name = "inn")
    private String inn;

}