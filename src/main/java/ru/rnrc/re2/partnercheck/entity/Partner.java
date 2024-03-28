package ru.rnrc.re2.partnercheck.entity;

import io.swagger.v3.oas.annotations.Parameter;
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
    @Parameter(example = "99999999")
    @Column(name = "inn")
    private String inn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partner", cascade = CascadeType.ALL)
    private List<LinkedPartner> beneficiary;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partner", cascade = CascadeType.ALL)
    private List<LinkedPartner> benefitHolder;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partner", cascade = CascadeType.ALL)
    private List<LinkedPartner> representative;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partner", cascade = CascadeType.ALL)
    private List<LinkedPartner> managementBodyList;

    public List<LinkedPartner> getBeneficiary() {
        if (beneficiary == null) {
            beneficiary = new ArrayList<>();
        }
        return beneficiary;
    }

    public List<LinkedPartner> getBenefitHolder() {
        if (benefitHolder == null) {
            benefitHolder = new ArrayList<>();
        }
        return benefitHolder;
    }

    public List<LinkedPartner> getRepresentative() {
        if (representative == null) {
            representative = new ArrayList<>();
        }
        return representative;
    }

    public List<LinkedPartner> getManagementBodyList() {
        if (managementBodyList == null) {
            managementBodyList = new ArrayList<>();
        }
        return managementBodyList;
    }
}