package ru.rnrc.re2.partnercheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rnrc.re2.partnercheck.dto.LinkedPartnerDTO;
import ru.rnrc.re2.partnercheck.dto.PartnerDTO;
import ru.rnrc.re2.partnercheck.entity.LinkedPartner;
import ru.rnrc.re2.partnercheck.entity.Partner;
import ru.rnrc.re2.partnercheck.mapper.LinkedPartnerMapper;
import ru.rnrc.re2.partnercheck.mapper.PartnerMapper;
import ru.rnrc.re2.partnercheck.repository.PartnerRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PartnerServiceImpl implements PartnerService {
    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;
    private final LinkedPartnerMapper linkedPartnerMapper;

    public void add(PartnerDTO contractorDTO) {
        Partner partner = partnerMapper.toPartner(contractorDTO);
        partnerRepository.save(partner);
        for (LinkedPartnerDTO linkedPartnerDTO : contractorDTO.getLinkedPartner()) {
            LinkedPartner linkedPartner = linkedPartnerMapper.toLinkedPartner(linkedPartnerDTO);
            linkedPartner.setPzinskey(formPzinskey(partner, linkedPartnerDTO));
            linkedPartner.setPartner(partner);
            partner.getLinkedPartner().add(linkedPartner);
        }
        partnerRepository.save(partner);
    }

    private String formPzinskey(Partner partner, LinkedPartnerDTO linkedPartnerDTO) {
        return "INDEX-OLR-PARTNERLINKEDSTRUCTURE-RNRC-DATA-REF-PARTNER "
                + partner.getId() + "!"
                + (partner.getLinkedPartner().size() + 1) + "!"
                + convertTypeToStructureKey(linkedPartnerDTO.getLinkedstructuretype(), linkedPartnerDTO.getBenefitPersonType(), linkedPartnerDTO.getManagementBodyType());
    }

    private String convertTypeToStructureKey(String linkedstructuretype, int benefitPersonType, int managementBodyType) {
        switch (linkedstructuretype) {
            case "Beneficiary":
                return "BENEFICIARYPARTNERLINKEDSTRUCTURE";
            case "BenefitHolder":
                if (benefitPersonType == 2) {
                    return "LEGALPERSONBENEFITHOLDERPARTNERLINKEDSTRUCTURE";
                } else if (benefitPersonType == 1) {
                    return "PERSONBENEFITHOLDERPARTNERLINKEDSTRUCTURE";
                }
            case "ManagementBody":
                if (managementBodyType == 3) {
                    return "MANAGEMENTBODYLEGALPERSON";
                } else if (managementBodyType == 2 || managementBodyType == 1) {
                    return "MANAGEMENTBODYPERSON";
                }
            case "Representative":
                return "REPRESENTATIVEPARTNERLINKEDSTRUCTURE";
            default:
                return "";
        }
    }
}

