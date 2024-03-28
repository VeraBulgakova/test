package ru.rnrc.re2.partnercheck.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.rnrc.re2.partnercheck.dto.response.ResponseDTO;
import ru.rnrc.re2.partnercheck.service.ResponseService;

import javax.xml.bind.JAXBException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@SecurityRequirement(name = "basicAuth")
public class ParserController {
    private final ResponseService responseService;

    @PostMapping("/partners/check")
    public ResponseEntity<List<ResponseDTO>> checkPartners(@RequestParam("file") MultipartFile file, @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate checkDate) throws JAXBException {
        return ResponseEntity.ok(responseService.checkPartners(file, checkDate));
    }

    @PostMapping("/terrorists/upload")
    public ResponseEntity<String> uploadXmlFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("fileType") String type) {
        return ResponseEntity.ok(responseService.uploadXmlFile(file, type));
    }

}
