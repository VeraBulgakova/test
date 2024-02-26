package com.vera.rnrc.service;

import com.vera.rnrc.entity.PartnerPhysicalEntity;
import com.vera.rnrc.repository.PartnerPhysicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PartnerPhysicalServiceImpl implements PartnerPhysicalService {
    private final PartnerPhysicalRepository partnerPhysicalRepository;

    public void addContractor(PartnerPhysicalEntity contractor) {
        partnerPhysicalRepository.save(contractor);
        partnerPhysicalRepository.save(convertToEntity(contractor));
    }

    private PartnerPhysicalEntity convertToEntity(PartnerPhysicalEntity contractor) {
        contractor.setPzinskey("INDEX-OLR-PARTNERLINKEDSTRUCTURE-RNRC-DATA-REF-PARTNER " + contractor.getId() + "!" + contractor.convertTypeToStructureKey());
        contractor.setDateOfBirth(convertDate(contractor.getDateOfBirth()));
        return contractor;
    }

    private String convertDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");

        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
