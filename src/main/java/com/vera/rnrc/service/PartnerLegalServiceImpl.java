package com.vera.rnrc.service;

import com.vera.rnrc.entity.PartnerLegalEntity;
import com.vera.rnrc.repository.PartnerLegalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartnerLegalServiceImpl implements PartnerLegalService {
    private final PartnerLegalRepository partnerLegalRepository;

    public void addContractor(PartnerLegalEntity contractor) {
        partnerLegalRepository.save(contractor);
        partnerLegalRepository.save(convertToEntity(contractor));
    }

    private PartnerLegalEntity convertToEntity(PartnerLegalEntity contractor) {
        contractor.setPartnerId(String.valueOf(contractor.getId()));
        return contractor;
    }
}
