package ru.rnrc.re2.partnercheck.mapper;

import org.springframework.stereotype.Component;
import ru.rnrc.re2.partnercheck.dto.PartnerDTO;
import ru.rnrc.re2.partnercheck.entity.Partner;

@Component
public class PartnerMapper {
    public Partner toPartner(PartnerDTO partnerDTO) {
        Partner partner = new Partner();
        partner.setPartnername(partnerDTO.getPartnername());
        partner.setFullname(partnerDTO.getFullname());
        partner.setOgrn(partnerDTO.getOgrn());
        partner.setInn(partnerDTO.getInn());
        return partner;
    }
}
