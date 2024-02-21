package com.vera.rnrc.service;

import com.vera.rnrc.mapper.LegalPersonMapper;
import com.vera.rnrc.mapper.PhysicalPersonMapper;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ROMUServiceTest {
    PhysicalPersonRepository physicalPersonRepository = Mockito.mock(PhysicalPersonRepository.class);
    LegalPersonRepository legalPersonRepository = Mockito.mock(LegalPersonRepository.class);
    LegalPersonMapper legalPersonMapper = Mockito.mock(LegalPersonMapper.class);
    PhysicalPersonMapper physicalPersonMapper = Mockito.mock(PhysicalPersonMapper.class);

    @Test
    void saveAllTest() {
        ROMUServiceImpl romuService = new ROMUServiceImpl(physicalPersonRepository, legalPersonRepository, legalPersonMapper, physicalPersonMapper);

        romuService.saveAll(null, "fileName", "type");

        Mockito.verify(legalPersonRepository, Mockito.times(1)).saveAll(Mockito.any());
        Mockito.verify(physicalPersonRepository, Mockito.times(1)).saveAll(Mockito.any());
    }
}
