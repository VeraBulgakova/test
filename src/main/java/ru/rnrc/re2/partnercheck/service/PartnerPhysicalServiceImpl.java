package ru.rnrc.re2.partnercheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rnrc.re2.partnercheck.entity.PartnerPhysical;
import ru.rnrc.re2.partnercheck.repository.PartnerPhysicalRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class PartnerPhysicalServiceImpl implements PartnerPhysicalService {
    private final PartnerPhysicalRepository partnerPhysicalRepository;

    public void addContractor(PartnerPhysical contractor) {
        partnerPhysicalRepository.save(contractor);
        partnerPhysicalRepository.save(convertToEntity(contractor));
    }

    private PartnerPhysical convertToEntity(PartnerPhysical contractor) {
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
