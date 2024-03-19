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

import java.util.List;

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
        int indexCount = 1;
        saveLinkedPartner(contractorDTO.getRepresentative(), partner, indexCount);
        saveLinkedPartner(contractorDTO.getBenefitHolder(), partner, indexCount);
        saveLinkedPartner(contractorDTO.getBeneficiary(), partner, indexCount);
        saveLinkedPartner(contractorDTO.getManagementBodyList(), partner, indexCount);
        partnerRepository.save(partner);
    }

    private String formPzinskey(Partner partner, LinkedPartnerDTO linkedPartnerDTO, int indexCount) {
        return "INDEX-OLR-PARTNERLINKEDSTRUCTURE-RNRC-DATA-REF-PARTNER "
                + partner.getId() + "!"
                + (indexCount) + "!"
                + convertTypeToStructureKey(linkedPartnerDTO.getLinkedstructuretype(), linkedPartnerDTO.getBenefitPersonType(), linkedPartnerDTO.getManagementBodyType());
    }

    private void saveLinkedPartner(List<LinkedPartnerDTO> linkedPartners, Partner partner, int indexCount) {
        for (int i = 0; i < linkedPartners.size(); i++) {
            LinkedPartnerDTO linkedPartnerDTO = linkedPartners.get(i);
            LinkedPartner linkedPartner = linkedPartnerMapper.toLinkedPartner(linkedPartnerDTO);
            linkedPartner.setPzinskey(formPzinskey(partner, linkedPartnerDTO, indexCount));
            linkedPartner.setParticipantordernumber(String.valueOf(i + 1));
            if (linkedPartners.get(i).getLinkedstructuretype().equals("ManagementBody")) {
                linkedPartner.setManagementbodyordernumber(String.valueOf(i + 1));
            }
            linkedPartner.setPartner(partner);
            if (linkedPartners.get(i).getLinkedstructuretype().equals("ManagementBody")) {
                partner.getManagementBodyList().add(linkedPartner);
            } else if (linkedPartners.get(i).getLinkedstructuretype().equals("BenefitHolder")) {
                partner.getBenefitHolder().add(linkedPartner);
            } else if (linkedPartners.get(i).getLinkedstructuretype().equals("Beneficiary")) {
                partner.getBeneficiary().add(linkedPartner);
            } else if (linkedPartners.get(i).getLinkedstructuretype().equals("Representative")) {
                partner.getRepresentative().add(linkedPartner);
            }
            indexCount++;
        }

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

