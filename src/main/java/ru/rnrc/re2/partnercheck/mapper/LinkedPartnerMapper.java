package ru.rnrc.re2.partnercheck.mapper;

import org.springframework.stereotype.Component;
import ru.rnrc.re2.partnercheck.dto.LinkedPartnerDTO;
import ru.rnrc.re2.partnercheck.entity.LinkedPartner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class LinkedPartnerMapper {
    public LinkedPartner toLinkedPartner(LinkedPartnerDTO linkedPartnerDTO) {
        LinkedPartner linkedPartner = new LinkedPartner();
        linkedPartner.setInn(linkedPartnerDTO.getInn());
        linkedPartner.setDocSeries(linkedPartnerDTO.getDocSeries());
        linkedPartner.setDocNumber(linkedPartnerDTO.getDocNumber());
        linkedPartner.setFullname(linkedPartnerDTO.getFullname());
        linkedPartner.setLastname(linkedPartnerDTO.getLastname());
        linkedPartner.setFirstname(linkedPartnerDTO.getFirstname());
        linkedPartner.setMiddlename(linkedPartnerDTO.getMiddlename());
        linkedPartner.setDateOfBirth(convertDate(linkedPartnerDTO.getDateOfBirth()));
        linkedPartner.setPlaceOfBirth(linkedPartnerDTO.getPlaceOfBirth());
        linkedPartner.setLinkedstructuretype(linkedPartnerDTO.getLinkedstructuretype());
//        linkedPartner.setManagementbodyordernumber(String.valueOf(linkedPartnerDTO.getManagementBodyType()));
//        linkedPartner.setParticipantordernumber(String.valueOf(linkedPartnerDTO.getBenefitPersonType()));
        return linkedPartner;
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
