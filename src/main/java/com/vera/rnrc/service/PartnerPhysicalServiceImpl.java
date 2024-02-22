package com.vera.rnrc.service;

import com.vera.rnrc.entity.PartnerPhysicalEntity;
import com.vera.rnrc.repository.PartnerPhysicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartnerPhysicalServiceImpl implements PartnerPhysicalService {
    private final PartnerPhysicalRepository partnerPhysicalRepository;

    public void addContractor(PartnerPhysicalEntity contractor) {
        partnerPhysicalRepository.save(contractor);
    }

}
