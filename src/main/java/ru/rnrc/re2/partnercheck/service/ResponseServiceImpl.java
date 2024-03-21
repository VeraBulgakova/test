package ru.rnrc.re2.partnercheck.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.rnrc.re2.partnercheck.dto.SubjectDTO;
import ru.rnrc.re2.partnercheck.dto.mvk.ActualDecisionsListDTO;
import ru.rnrc.re2.partnercheck.dto.mvk.DecisionDTO;
import ru.rnrc.re2.partnercheck.dto.mvk.MVKPerechenDTO;
import ru.rnrc.re2.partnercheck.dto.mvk.SubjectListDTO;
import ru.rnrc.re2.partnercheck.dto.request.RequestDTO;
import ru.rnrc.re2.partnercheck.dto.response.ResponseDTO;
import ru.rnrc.re2.partnercheck.dto.romu.ROMUPerechenDTO;
import ru.rnrc.re2.partnercheck.dto.terror.TERRORPerechenDTO;
import ru.rnrc.re2.partnercheck.entity.Response;
import ru.rnrc.re2.partnercheck.mapper.ResponseMapper;
import ru.rnrc.re2.partnercheck.repository.ResponseRepository;

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
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResponseServiceImpl implements ResponseService {
    private final TerrorService terrorService;
    private final MVKService mvkService;
    private final ROMUService romuService;
    private final ResponseRepository responseRepository;
    private final ResponseMapper responseMapper;
    private static final Logger businessLogger = LogManager.getLogger("jdbc");
    private static final org.slf4j.Logger errorLogger = LoggerFactory.getLogger(ResponseServiceImpl.class);

    @Override
    public List<ResponseDTO> getCheckResponseForPartners(RequestDTO request, LocalDate checkDate) {
        if (checkDate == null) {
            checkDate = LocalDate.now();
        }
        String date = checkDate.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String dateNow = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));

        responseRepository.insertResponseRecordsFromTableForLegalPerson(request.getPerchenListDTO().getListName(), date);
        responseRepository.insertResponseRecordsFromTableForPhysicalPerson(request.getPerchenListDTO().getListName(), date);
        List<Response> matchingRecords;
        if (request.isAllPartners()) {
            matchingRecords = responseRepository.findAll();
        } else {
            matchingRecords = responseRepository.findAllByPartnerId(request.getPartnerId());
        }
        businessLogger.info("Дата проверки: {}. Название перечня: {}. Количество совпадений: {} ",
                date, request.getPerchenListDTO().getListName(),
                responseRepository.findMatchingRecordsCountForLegalPerson(request.getPerchenListDTO().getListName(), date)
                        + responseRepository.findMatchingRecordsCountForPhysicalPerson(request.getPerchenListDTO().getListName(), date));
        responseRepository.cleanResultTable();
        businessLogger.info("Таблица результатов очищена");
        return responseMapper.toResponseDTOList(matchingRecords, request, dateNow, date);
    }

    @Override
    public String uploadXmlFile(MultipartFile file, String type) {
        if (file.isEmpty()) {
            errorLogger.error("File is not provided");
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
                    businessLogger.info("Тип обработки: '{}'. Количество элементов в перечне Террор: {}", type, terrorPerechenDTO.getActualPerechenDTO().getSubjectsDTO().size());
                    break;
                case "МВК":
                    MVKPerechenDTO MVKDecisionList = processXmlFile(file, MVKPerechenDTO.class, false);
                    mvkService.saveAll(MVKDecisionList, fileName, type);
                    businessLogger.info("Тип обработки: '{}'. Количество элементов в перечне МВК: {}", type, getSubjectsList(MVKDecisionList).size());
                    break;
                case "РОМУ":
                    ROMUPerechenDTO ROMUPerechenDTO = processXmlFile(file, ROMUPerechenDTO.class, true);
                    romuService.saveAll(ROMUPerechenDTO, fileName, type);
                    businessLogger.info("Тип обработки: '{}'. Количество элементов в перечне РОМУ: {}", type, ROMUPerechenDTO.getEntities().size());
                    break;
                default:
                    errorLogger.error("Unknown file type");
                    return "Неизвестный тип файла";
            }
        } catch (Exception e) {
            errorLogger.error("Error while processing file: {}", e.getMessage());
            return "Ошибка при обработке файла: " + e.getMessage();
        }
        return "Файл успешно обработан";
    }

    @Override
    public List<ResponseDTO> checkPartners(MultipartFile file, LocalDate checkDate) throws JAXBException {
        if (file.isEmpty()) {
            errorLogger.error("File is not provided");
            return List.of(new ResponseDTO());
        }
        try {
            RequestDTO jaxbObject = processXmlFile(file, RequestDTO.class, false);
            return getCheckResponseForPartners(jaxbObject, checkDate);
        } catch (Exception e) {
            errorLogger.error("Error while processing file: {}", e.getMessage());
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

    private List<SubjectDTO> getSubjectsList(MVKPerechenDTO jaxbObject) {
        return Optional.ofNullable(jaxbObject.getActualDecisionsListDTO())
                .map(ActualDecisionsListDTO::getDecisions)
                .stream()
                .flatMap(List::stream)
                .map(DecisionDTO::getSubjectListDTO)
                .filter(Objects::nonNull)
                .map(SubjectListDTO::getSubjectsDTO)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .toList();
    }
}
