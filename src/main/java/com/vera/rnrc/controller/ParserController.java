package com.vera.rnrc.controller;

import com.vera.rnrc.dto.terror.Perechen;
import com.vera.rnrc.dto.terror.Subject;
import com.vera.rnrc.entity.LegalPersonEntity;
import com.vera.rnrc.entity.PhysicalPersonEntity;
import com.vera.rnrc.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ParserController {

    private final SubjectService subjectService;

    @Autowired
    public ParserController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public void terror() throws JAXBException, FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\bulga\\Downloads\\testFile.xml"));
        String body = br.lines().collect(Collectors.joining());
        StringReader reader = new StringReader(body);
        JAXBContext context = JAXBContext.newInstance(Perechen.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Perechen screen = (Perechen) unmarshaller.unmarshal(reader);
        System.out.println(screen.getActualPerechen().getSubjects().get(0).getFl().getDocumentList());
//        System.out.println(screen.getLatestExcluded().getIncludedExcludedEntities().get(0));
    }

    @PostMapping("/uploadXml")
    public ResponseEntity<String> uploadXmlFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("fileType") String type) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Файл не предоставлен");
        }

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            reader.readLine();
            JAXBContext context = JAXBContext.newInstance(Perechen.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Perechen jaxbObject = (Perechen) unmarshaller.unmarshal(reader);
            String fileName = file.getOriginalFilename();
            int lastIndexOfDot = fileName.lastIndexOf(".");
            if (lastIndexOfDot > 0) {
                fileName = fileName.substring(0, lastIndexOfDot);
            }
            String finalFileName = fileName;
            // Предполагается, что у вас есть метод для получения наименования субъекта
            List<Subject> subjectsList = jaxbObject.getActualPerechen().getSubjects();
            List<PhysicalPersonEntity> physicalPersonEntities = subjectsList.stream()
                    .filter(subject -> subject.getSubjectType().getName().contains("Физическое лицо"))
                    .map(subject -> subjectService.convertToPhysicalPersonList(subject, finalFileName, type))
                    .toList();
            List<LegalPersonEntity> legalPersonEntities = subjectsList.stream()
                    .filter(subject -> subject.getSubjectType().getName().contains("Юридическое лицо"))
                    .map(subject -> subjectService.convertToLegalEntityList(subject, finalFileName, type))
                    .toList();

            subjectService.saveAllLegalPerson(legalPersonEntities);
            subjectService.saveAllPhysicalPersons(physicalPersonEntities);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Ошибка при обработке файла: " + e.getMessage());
        }

        return ResponseEntity.ok("Файл успешно обработан");
    }

    @GetMapping("/getLegalEntityData")
    public ResponseEntity<String> getLegalEntityData(@RequestParam("entityId") String entityId,
                                                     @RequestParam("date") Optional<LocalDate> date) {
        // Логика получения данных для юридического лица.
        // Если дата не предоставлена, используется последняя доступная дата.
//        LocalDate queryDate = date.orElseGet(this::getLastAvailableDate);

        // Получение данных по entityId и queryDate
        // Например, из базы данных или внутреннего сервиса

        return ResponseEntity.ok("Данные для юридического лица " + entityId + " на дату ");
    }
}
