package ru.rnrc.re2.partnercheck.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "rnrc_ref_partner")
@NoArgsConstructor
@AllArgsConstructor
public class PartnerLegal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partnerLegal")
    private List<PartnerPhysical> partnerPhysicals;

}