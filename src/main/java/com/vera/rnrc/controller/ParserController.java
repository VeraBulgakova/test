package com.vera.rnrc.controller;

import com.vera.rnrc.dto.mvk.MVKPerechenDTO;
import com.vera.rnrc.dto.request.RequestDTO;
import com.vera.rnrc.dto.response.ResponseDTO;
import com.vera.rnrc.dto.romu.ROMUPerechen;
import com.vera.rnrc.dto.terror.TERRORPerechenDTO;
import com.vera.rnrc.service.*;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ParserController {
    private final ResponseService responseService;
    private final TerrorService terrorService;
    private final MVKService mvkService;
    private final ROMUService romuService;

    @Autowired
    public ParserController(ResponseServiceImpl responseService, TerrorServiceImpl terrorServiceImpl, MVKServiceImpl mvkServiceImpl, ROMUServiceImpl romuServiceImpl) {
        this.responseService = responseService;
        this.terrorService = terrorServiceImpl;
        this.mvkService = mvkServiceImpl;
        this.romuService = romuServiceImpl;
    }

    @PostMapping("/checkPartners")
    public List<ResponseDTO> checkPartners(@RequestParam("file") MultipartFile file, @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate checkDate) throws JAXBException {
        if (file.isEmpty()) {
            return Collections.singletonList(new ResponseDTO());
        }
        try {
            RequestDTO jaxbObject = processXmlFile(file, RequestDTO.class, false);
            return responseService.getCheckResponseForPartners(jaxbObject, checkDate);
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
                    TERRORPerechenDTO terrorPerechenDTO = processXmlFile(file, TERRORPerechenDTO.class, true);
                    terrorService.saveAll(terrorPerechenDTO, fileName, type);
                    break;
                case "МВК":
                    MVKPerechenDTO MVKDecisionList = processXmlFile(file, MVKPerechenDTO.class, false);
                    mvkService.saveAll(MVKDecisionList, fileName, type);
                    break;
                case "РОМУ":
                    ROMUPerechen ROMUPerechen = processXmlFile(file, ROMUPerechen.class, true);
                    romuService.saveAll(ROMUPerechen, fileName, type);
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
