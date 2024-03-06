package ru.rnrc.re2.partnercheck.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rnrc.re2.partnercheck.mapper.LegalPersonMapper;
import ru.rnrc.re2.partnercheck.mapper.PhysicalPersonMapper;
import ru.rnrc.re2.partnercheck.repository.LegalPersonRepository;
import ru.rnrc.re2.partnercheck.repository.PhysicalPersonRepository;

import static org.mockito.Mockito.verify;

public class TerrorServiceTest {
    PhysicalPersonRepository physicalPersonRepository = Mockito.mock(PhysicalPersonRepository.class);
    LegalPersonRepository legalPersonRepository = Mockito.mock(LegalPersonRepository.class);
    LegalPersonMapper legalPersonMapper = Mockito.mock(LegalPersonMapper.class);
    PhysicalPersonMapper physicalPersonMapper = Mockito.mock(PhysicalPersonMapper.class);

    @Test
    void saveAllTest() {
        TerrorServiceImpl terrorService = new TerrorServiceImpl(physicalPersonRepository, legalPersonRepository, legalPersonMapper, physicalPersonMapper);

        terrorService.saveAll(null, "fileName", "type");

        verify(legalPersonRepository, Mockito.times(1)).saveAll(Mockito.any());
        verify(physicalPersonRepository, Mockito.times(1)).saveAll(Mockito.any());
    }
}
