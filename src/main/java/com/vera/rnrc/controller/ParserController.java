package com.vera.rnrc.controller;

import com.vera.rnrc.dto.response.ResponseDTO;
import com.vera.rnrc.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ParserController {
    private final ResponseService responseService;

    @PostMapping("/checkPartners")
    public ResponseEntity<List<ResponseDTO>> checkPartners(@RequestParam("file") MultipartFile file, @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate checkDate) throws JAXBException {
        return ResponseEntity.ok(responseService.checkPartners(file, checkDate));
    }

    @PostMapping("/uploadXml")
    public ResponseEntity<String> uploadXmlFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("fileType") String type) {
        return ResponseEntity.ok(responseService.uploadXmlFile(file, type));
    }

}
