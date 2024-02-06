package com.vera.rnrc.controller;

import com.vera.rnrc.dto.mvk.MVKDecisionListDTO;
import com.vera.rnrc.dto.request.QlikViewRequest;
import com.vera.rnrc.dto.response.ResponseDTO;
import com.vera.rnrc.dto.romu.ConsolidatedListDTO;
import com.vera.rnrc.dto.terror.Perechen;
import com.vera.rnrc.service.MVKService;
import com.vera.rnrc.service.ROMUService;
import com.vera.rnrc.service.ResponseService;
import com.vera.rnrc.service.TerrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ParserController {
    private final ResponseService service;
    private final TerrorService terrorService;
    private final MVKService mvkService;
    private final ROMUService romuService;

    @Autowired
    public ParserController(ResponseService service, TerrorService terrorService, MVKService mvkService, ROMUService romuService) {
        this.service = service;
        this.terrorService = terrorService;
        this.mvkService = mvkService;
        this.romuService = romuService;
    }

    @PostMapping("/checkUsers")
    public List<ResponseDTO> checkUsers(@RequestParam("file") MultipartFile file, @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate checkDate) throws JAXBException {
        if (file.isEmpty()) {
            return Collections.singletonList(new ResponseDTO());
        }
        try {
            QlikViewRequest jaxbObject = processXmlFile(file, QlikViewRequest.class, false);
            return service.getCheckResponseForAllPerson(jaxbObject, checkDate);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonList(new ResponseDTO());
        }
    }

    @PostMapping("/checkUsersTwoRequest")
    public List<ResponseDTO> checkUsersTwoRequest(@RequestParam("file") MultipartFile file, @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate checkDate) throws JAXBException {
        if (file.isEmpty()) {
            return Collections.singletonList(new ResponseDTO());
        }
        try {
            QlikViewRequest jaxbObject = processXmlFile(file, QlikViewRequest.class, false);
           List<ResponseDTO> resultList= service.getCheckResponseForPhysic(jaxbObject, checkDate);
            service.createViewForPhysicPerson("01.01.2021", "Террор");
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonList(new ResponseDTO());
        }
    }

    @PostMapping("/checkPhysical")
    public List<ResponseDTO> checkPhysical(@RequestParam("file") MultipartFile file, @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate checkDate) throws JAXBException {
        if (file.isEmpty()) {
            return Collections.singletonList(new ResponseDTO());
        }
        try {
            QlikViewRequest jaxbObject = processXmlFile(file, QlikViewRequest.class, false);
            List<ResponseDTO> responseList = new ArrayList<>();
            responseList.addAll(service.getCheckResponseForPhysic(jaxbObject, checkDate));
            return responseList;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonList(new ResponseDTO());
        }
    }

    @PostMapping("/checkLegal")
    public List<ResponseDTO> checkLegal(@RequestParam("file") MultipartFile file, @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate checkDate) throws JAXBException {
        if (file.isEmpty()) {
            return Collections.singletonList(new ResponseDTO());
        }
        try {
            QlikViewRequest jaxbObject = processXmlFile(file, QlikViewRequest.class, false);
            List<ResponseDTO> responseList = new ArrayList<>();
            responseList.addAll(service.getCheckResponseForLegal(jaxbObject, checkDate));
            return responseList;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonList(new ResponseDTO());
        }
    }

    @PostMapping("/uploadXml")
    public ResponseEntity<String> uploadXmlFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("fileType") String type) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Файл не предоставлен");
        }
        String fileName = Optional.ofNullable(file.getOriginalFilename())
                .map(name -> name.substring(0, name.lastIndexOf(".")))
                .orElse("");
        try {
            switch (type) {
                case "Террор":
                    Perechen perechen = processXmlFile(file, Perechen.class, true);
                    terrorService.saveAll(perechen, fileName, type);
                    break;
                case "МВК":
                    MVKDecisionListDTO MVKDecisionList = processXmlFile(file, MVKDecisionListDTO.class, false);
                    mvkService.saveAll(MVKDecisionList, fileName, type);
                    break;
                case "РОМУ":
                    ConsolidatedListDTO consolidatedListDTO = processXmlFile(file, ConsolidatedListDTO.class, true);
                    romuService.saveAll(consolidatedListDTO, fileName, type);
                    break;
                default:
                    return ResponseEntity.badRequest().body("Неизвестный тип файла");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Ошибка при обработке файла: " + e.getMessage());
        }

        return ResponseEntity.ok("Файл успешно обработан");
    }

    private static <T> T processXmlFile(MultipartFile file, Class<T> clazz, boolean skipFirstLine) throws IOException, JAXBException {
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
