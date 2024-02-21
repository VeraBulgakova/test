package com.vera.rnrc.service;

import com.vera.rnrc.dto.request.PerchenListDTO;
import com.vera.rnrc.dto.request.RequestDTO;
import com.vera.rnrc.dto.response.ResponseDTO;
import com.vera.rnrc.entity.ResponseEntity;
import com.vera.rnrc.mapper.ResponseMapper;
import com.vera.rnrc.repository.ResponseRepository;
import com.vera.rnrc.repository.ResponseRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class ResponseServiceTest {
    private final TerrorService mockTerrorService = Mockito.mock(TerrorService.class);
    private final MVKService mockMvkService = Mockito.mock(MVKService.class);
    private final ResponseMapper responseMapper = Mockito.mock(ResponseMapper.class);
    private final ROMUService mockRomuService = Mockito.mock(ROMUService.class);
    private final ResponseRepository responseRepository = Mockito.mock(ResponseRepository.class);
    private final ResponseRepositoryImpl responseRepositoryImpl = Mockito.mock(ResponseRepositoryImpl.class);
    private final ResponseServiceImpl responseService = Mockito.spy(new ResponseServiceImpl(mockTerrorService, mockMvkService, mockRomuService, responseRepository, responseRepositoryImpl, responseMapper));
    private RequestDTO request;
    private LocalDate checkDate;
    private List<ResponseEntity> responseEntities;
    private List<ResponseDTO> expectedDTOs;

    @BeforeEach
    void setUp() {
        // Инициализация тестовых данных
        request = new RequestDTO();
        checkDate = LocalDate.now();
        responseEntities = List.of(new ResponseEntity());
        expectedDTOs = List.of(new ResponseDTO());
        request.setPerchenListDTO(new PerchenListDTO());
        request.getPerchenListDTO().setTerroristList(true);

        when(responseRepository.findAll()).thenReturn(responseEntities);
        when(responseRepository.findAllByPartnerId(anyString())).thenReturn(responseEntities);
        when(responseMapper.toResponseDTOList(anyList(), any(RequestDTO.class), anyString(), anyString())).thenReturn(expectedDTOs);
    }

    @Test
    void getCheckResponseForPartners_withNullCheckDate() {
        List<ResponseDTO> result = responseService.getCheckResponseForPartners(request, null);

        assertSame(expectedDTOs, result);

        verify(responseRepository, times(1)).insertResponseRecordsFromTable();
        verify(responseRepository, times(1)).cleanResultTable();
        verify(responseMapper, times(1)).toResponseDTOList(anyList(), eq(request), anyString(), anyString());
    }

    @Test
    void getCheckResponseForPartners_withSpecificCheckDate() {
        List<ResponseDTO> result = responseService.getCheckResponseForPartners(request, checkDate);

        assertSame(expectedDTOs, result);
    }

    @Test
    void getCheckResponseForPartners_isAllPartnersTrue() {
        request.setAllPartners(true);

        responseService.getCheckResponseForPartners(request, checkDate);

        verify(responseRepository, times(1)).findAll();
    }

    @Test
    void getCheckResponseForPartners_isAllPartnersFalse() {
        request.setAllPartners(false);
        request.setPartnerId("specificPartnerId");

        responseService.getCheckResponseForPartners(request, checkDate);

        verify(responseRepository, times(1)).findAllByPartnerId("specificPartnerId");
    }

    @Test
    void uploadXmlFile_ShouldReturnErrorForEmptyFile() {
        MultipartFile emptyFile = new MockMultipartFile("file", "", "text/xml", new byte[0]);
        String result = responseService.uploadXmlFile(emptyFile, "Террор");
        assertEquals("Файл не предоставлен", result);
    }

    @Test
    void uploadXmlFile_ShouldReturnErrorForUnknownType() {
        MultipartFile file = new MockMultipartFile("file", "filename.xml", "text/xml", "<xml></xml>".getBytes());
        String result = responseService.uploadXmlFile(file, "Неизвестный");
        assertEquals("Неизвестный тип файла", result);
    }
    @Test
    void checkPartners_ShouldReturnEmptyListWhenFileIsEmpty() throws JAXBException {
        MultipartFile emptyFile = new MockMultipartFile("file", new byte[0]);

        List<ResponseDTO> result = responseService.checkPartners(emptyFile, LocalDate.now());

        assertEquals(1, result.size());
    }
}
