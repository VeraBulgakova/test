package ru.rnrc.re2.partnercheck.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "rnrc_ref_partner")
@NoArgsConstructor
@AllArgsConstructor
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "partnername")
    private String partnername;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "ogrn")
    private String ogrn;

    @Column(name = "inn")
    private String inn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partner", cascade = CascadeType.ALL)
    private List<LinkedPartner> linkedPartner;

    public List<LinkedPartner> getLinkedPartner() {
        if (linkedPartner == null) {
            linkedPartner = new ArrayList<>();
        }
        return linkedPartner;
    }
}