package com.vera.rnrc.service;

import com.vera.rnrc.dto.mvk.MVKPerechenDTO;
import com.vera.rnrc.mapper.LegalPersonMapper;
import com.vera.rnrc.mapper.PhysicalPersonMapper;
import com.vera.rnrc.repository.LegalPersonRepository;
import com.vera.rnrc.repository.PhysicalPersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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