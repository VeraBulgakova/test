package ru.rnrc.re2.partnercheck.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rnrc.re2.partnercheck.mapper.PersonMapper;
import ru.rnrc.re2.partnercheck.mapper.PhysicalPersonMapper;
import ru.rnrc.re2.partnercheck.repository.LegalPersonRepository;
import ru.rnrc.re2.partnercheck.repository.PhysicalPersonRepository;

public class ROMUServiceTest {
    PhysicalPersonRepository physicalPersonRepository = Mockito.mock(PhysicalPersonRepository.class);
    LegalPersonRepository legalPersonRepository = Mockito.mock(LegalPersonRepository.class);
    PersonMapper personMapper = Mockito.mock(PersonMapper.class);
    PhysicalPersonMapper physicalPersonMapper = Mockito.mock(PhysicalPersonMapper.class);

    @Test
    void saveAllTest() {
        ROMUServiceImpl romuService = new ROMUServiceImpl(physicalPersonRepository, legalPersonRepository, personMapper, physicalPersonMapper);

        romuService.saveAll(null, "fileName", "type");

        Mockito.verify(legalPersonRepository, Mockito.times(1)).saveAll(Mockito.any());
        Mockito.verify(physicalPersonRepository, Mockito.times(1)).saveAll(Mockito.any());
    }
}
