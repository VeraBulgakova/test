package ru.rnrc.re2.partnercheck.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.rnrc.re2.partnercheck.dto.mvk.MVKPerechenDTO;
import ru.rnrc.re2.partnercheck.mapper.LegalPersonMapper;
import ru.rnrc.re2.partnercheck.mapper.PhysicalPersonMapper;
import ru.rnrc.re2.partnercheck.repository.LegalPersonRepository;
import ru.rnrc.re2.partnercheck.repository.PhysicalPersonRepository;

import static org.mockito.Mockito.*;

public class MVKServiceTest {
    PhysicalPersonRepository physicalPersonRepository = Mockito.mock(PhysicalPersonRepository.class);
    LegalPersonRepository legalPersonRepository = Mockito.mock(LegalPersonRepository.class);
    LegalPersonMapper legalPersonMapper = Mockito.mock(LegalPersonMapper.class);
    PhysicalPersonMapper physicalPersonMapper = Mockito.mock(PhysicalPersonMapper.class);
    private final MVKServiceImpl mvkService = Mockito.spy(new MVKServiceImpl(physicalPersonRepository, legalPersonRepository, legalPersonMapper, physicalPersonMapper) {
    });

    @Test
    void saveAllTest() {
        MVKPerechenDTO jaxbObject = new MVKPerechenDTO();

        mvkService.saveAll(jaxbObject, "finalFileName", "type");

        verify(legalPersonRepository, times(1)).saveAll(anyList());
        verify(physicalPersonRepository, times(1)).saveAll(anyList());
    }
}