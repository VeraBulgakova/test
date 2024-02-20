package com.vera.rnrc.service;

import com.vera.rnrc.dto.mvk.MVKPerechenDTO;
import com.vera.rnrc.dto.request.RequestDTO;
import com.vera.rnrc.dto.response.ResponseDTO;
import com.vera.rnrc.dto.romu.ROMUPerechenDTO;
import com.vera.rnrc.dto.terror.TERRORPerechenDTO;
import com.vera.rnrc.entity.ResponseEntity;
import com.vera.rnrc.mapper.ResponseMapper;
import com.vera.rnrc.repository.ResponseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private final TerrorService terrorService;
    private final MVKService mvkService;
    private final ROMUService romuService;
    private final ResponseRepository responseRepository;
    private final ResponseMapper responseMapper;
    private static final Logger logger = LogManager.getLogger("jdbc");
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void createViewForPhysicPerson(@Param("dateList") String dateList, @Param("listName") String listName) {
        String sql = responseRepository.createViewForPhysicalPerson(dateList, listName);
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Override
    @Transactional
    public void createViewForLegalPerson(@Param("dateList") String dateList, @Param("listName") String listName) {
        String sql = responseRepository.createViewForLegalPerson(dateList, listName);
        entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Override
    @Transactional
    public List<ResponseDTO> getCheckResponseForPartners(RequestDTO request, LocalDate checkDate) {
        if (checkDate == null) {
            checkDate = LocalDate.now();
        }
        String date = checkDate.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String dateNow = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

        createViewForPhysicPerson(date, request.getPerchenListDTO().getListName());
        createViewForLegalPerson(date, request.getPerchenListDTO().getListName());

        responseRepository.insertResponseRecordsFromTable();
        List<ResponseEntity> matchingRecords;
        if (request.isAllPartners()) {
            matchingRecords = responseRepository.findAll();

        } else {
            matchingRecords = responseRepository.findAllByPartnerId(request.getPartnerId());
        }
        responseRepository.cleanResultTable();
        responseRepository.cleanLegalPersonTable();
        responseRepository.cleanPhysicPersonTable();
        return responseMapper.toResponseDTOList(matchingRecords, request, dateNow, date);
    }

    @Override
    public String uploadXmlFile(MultipartFile file, String type) {
        if (file.isEmpty()) {
            logger.error("File is not provided");
            return "Файл не предоставлен";
        }
        String fileName = Optional.ofNullable(file.getOriginalFilename())
                .map(name -> name.substring(0, name.lastIndexOf(".")))
                .orElse("");
        try {
            switch (type) {
                case "Террор":
                    TERRORPerechenDTO terrorPerechenDTO = processXmlFile(file, TERRORPerechenDTO.class, true);
                    terrorService.saveAll(terrorPerechenDTO, fileName, type);
                    break;
                case "МВК":
                    MVKPerechenDTO MVKDecisionList = processXmlFile(file, MVKPerechenDTO.class, false);
                    mvkService.saveAll(MVKDecisionList, fileName, type);
                    break;
                case "РОМУ":
                    ROMUPerechenDTO ROMUPerechenDTO = processXmlFile(file, ROMUPerechenDTO.class, true);
                    romuService.saveAll(ROMUPerechenDTO, fileName, type);
                    break;
                default:
                    logger.error("Unknown file type");
                    return "Неизвестный тип файла";
            }
        } catch (Exception e) {
            logger.error("Error while processing file: {}", e.getMessage());
            return "Ошибка при обработке файла: " + e.getMessage();
        }
        return "Файл успешно обработан";
    }

    @Override
    @Transactional
    public List<ResponseDTO> checkPartners(MultipartFile file, LocalDate checkDate) throws JAXBException {
        if (file.isEmpty()) {
            logger.error("File is not provided");
            return List.of(new ResponseDTO());
        }
        try {
            RequestDTO jaxbObject = processXmlFile(file, RequestDTO.class, false);
            return getCheckResponseForPartners(jaxbObject, checkDate);
        } catch (Exception e) {
            logger.error("Error while processing file: {}", e.getMessage());
            return List.of(new ResponseDTO());
        }
    }

    private <T> T processXmlFile(MultipartFile file, Class<T> clazz, boolean skipFirstLine) throws IOException, JAXBException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
        if (skipFirstLine) {
            reader.readLine();
        }
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return clazz.cast(unmarshaller.unmarshal(reader));
    }
}
