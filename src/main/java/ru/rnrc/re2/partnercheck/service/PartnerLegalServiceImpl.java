package ru.rnrc.re2.partnercheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rnrc.re2.partnercheck.entity.PartnerLegal;
import ru.rnrc.re2.partnercheck.repository.PartnerLegalRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PartnerLegalServiceImpl implements PartnerLegalService {
    private final PartnerLegalRepository partnerLegalRepository;

    public void addContractor(PartnerLegal contractor) {
        partnerLegalRepository.save(contractor);
        partnerLegalRepository.save(convertToEntity(contractor));
    }

    private PartnerLegal convertToEntity(PartnerLegal contractor) {
        contractor.setPartnerId(String.valueOf(contractor.getId()));
        return contractor;
    }
}
