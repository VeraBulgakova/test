package ru.rnrc.re2.partnercheck.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
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

    @Operation(summary = "Check partners",
            description = "Loads a file with partner data and checks it for a given date. " +
                    "The file must be in XML format." +
                    "The review date must be provided in the format 'dd.MM.yyyy'.")
    @PostMapping(value = "/partners/check", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ResponseDTO>> checkPartners(@Parameter(
            description = "Need to attach a file to request verification of partners",
            required = true) @RequestParam("file") MultipartFile file, @Parameter(
            description = "Date to be checked in dd.MM.yyyy format",
            required = true) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate checkDate) throws JAXBException {
        return ResponseEntity.ok(responseService.checkPartners(file, checkDate));
    }

    @Operation(summary = "Upload xml file",
            description = "Accepts an XML file to process and a parameter indicating the file type (for example, \"Террор\", \"МВК\", \"РОМУ\")" +
                    "This method is used to upload data files that must then be verified.")
    @PostMapping(value = "/terrorists/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadXmlFile(@RequestParam("file") MultipartFile file,
                                                @Parameter(
                                                        name = "type",
                                                        description = "the type of file that is being downloaded",
                                                        example = "Террор",
                                                        required = true) @RequestParam("fileType") String type) {
        return ResponseEntity.ok(responseService.uploadXmlFile(file, type));
    }

}
