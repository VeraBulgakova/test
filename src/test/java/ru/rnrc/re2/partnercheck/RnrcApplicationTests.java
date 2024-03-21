package ru.rnrc.re2.partnercheck;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.rnrc.re2.partnercheck.dto.LinkedPartnerDTO;
import ru.rnrc.re2.partnercheck.dto.PartnerDTO;
import ru.rnrc.re2.partnercheck.entity.LegalPerson;
import ru.rnrc.re2.partnercheck.entity.PhysicalPerson;
import ru.rnrc.re2.partnercheck.repository.LegalPersonRepository;
import ru.rnrc.re2.partnercheck.repository.PartnerRepository;
import ru.rnrc.re2.partnercheck.repository.PhysicalPersonRepository;
import ru.rnrc.re2.partnercheck.repository.ResponseRepository;
import ru.rnrc.re2.partnercheck.service.PartnerService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RnrcApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private LegalPersonRepository legalPersonRepository;
    @Autowired
    private PhysicalPersonRepository physicalPersonRepository;
    @Autowired
    private PartnerRepository partnerRepository;
    @Autowired
    private PartnerService partnerService;


    @BeforeEach
    void setUp() {
        legalPersonRepository.deleteAll();
        partnerRepository.deleteAll();
        LegalPerson terroristLegal = new LegalPerson(5L,
                "10.01.2024",
                "Террор",
                "1031322002051",
                "1327153416",
                "МЕСТНАЯ РЕЛИГИОЗНАЯ ОРГАНИЗАЦИЯ СВИДЕТЕЛЕЙ ИЕГОВЫ \"САРАНСК\"");
        PhysicalPerson terroristPhysical = new PhysicalPerson(115L,
                "10.01.2024",
                "Террор",
                "4708010388",
                "3917",
                "306932",
                "ЗИЗА БОГДАН СЕРГЕЕВИЧ",
                "ЗИЗА",
                "БОГДАН",
                "СЕРГЕЕВИЧ",
                "19941123",
                "С. МЕНЧИКУРЫ ВЕСЕЛОВСКОГО РАЙОНА ЗАПОРОЖСКОЙ ОБЛАСТИ УКРАИНЫ");
        legalPersonRepository.saveAll(List.of(terroristLegal));
        physicalPersonRepository.saveAll(List.of(terroristPhysical));
    }


    @Test
    void findPartnersWhoAreOnTheListOfTerrorists() {
        PartnerDTO partner1 = new PartnerDTO(
                "МЕСТНАЯ РЕЛИГИОЗНАЯ ОРГАНИЗАЦИЯ СВИДЕТЕЛЕЙ ИЕГОВЫ \"САРАНСК\"",
                "МЕСТНАЯ РЕЛИГИОЗНАЯ ОРГАНИЗАЦИЯ СВИДЕТЕЛЕЙ ИЕГОВЫ \"САРАНСК\"",
                "1031322002051",
                "1327153416",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
        PartnerDTO partner2 = new PartnerDTO(
                null,
                null,
                null,
                "1327153416",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
        PartnerDTO partner3 = new PartnerDTO(
                null,
                null,
                "1031322002051",
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
        PartnerDTO partner4 = new PartnerDTO(
                "МЕСТНАЯ РЕЛИГИОЗНАЯ ОРГАНИЗАЦИЯ СВИДЕТЕЛЕЙ ИЕГОВЫ \"САРАНСК\"",
                "МЕСТНАЯ РЕЛИГИОЗНАЯ ОРГАНИЗАЦИЯ СВИДЕТЕЛЕЙ ИЕГОВЫ \"САРАНСК\"",
                null,
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
        partnerService.add(partner1);
        partnerService.add(partner2);
        partnerService.add(partner3);
        partnerService.add(partner4);
        Integer foundPartners = responseRepository.findMatchingRecordsCountForLegalPerson("Террор", "10.01.2024");
        assertEquals(4, foundPartners);
    }

    @Test
    void findLinkedPartnersWhoAreOnTheListOfTerrorists() {
        List<LinkedPartnerDTO> representatives = new ArrayList<>();
        LinkedPartnerDTO representative = new LinkedPartnerDTO(
                "911015567638",
                "3917",
                "306932",
                "Зиза Богдан Сергеевич",
                "Зиза",
                "Богдан",
                "Сергеевич",
                "23.11.1994",
                "Москва",
                "Representative",
                0,
                0
        );
        representatives.add(representative);
        PartnerDTO partner1 = new PartnerDTO(
                "ACE Insurance Brokers Pvt. Ltd., Индия",
                "ACE Insurance Brokers Pvt. Ltd.",
                null,
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                representatives,
                new ArrayList<>());
        partnerService.add(partner1);

        Integer foundPartners = responseRepository.findMatchingRecordsCountForPhysicalPerson("Террор", "10.01.2024");
        assertEquals(1, foundPartners);
    }

    @Test
    void findPartnersWhoAreNotOnTheListOfTerrorists() {
        PartnerDTO partner = new PartnerDTO(
                "ACE Insurance Brokers Pvt. Ltd., Индия",
                "ACE Insurance Brokers Pvt. Ltd.",
                null,
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());

        partnerService.add(partner);

        Integer foundPartners = responseRepository.findMatchingRecordsCountForLegalPerson("Террор", "10.01.2024");
        assertEquals(0, foundPartners);
    }

}
