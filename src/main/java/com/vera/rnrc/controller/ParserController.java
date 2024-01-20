package com.vera.rnrc.controller;

import com.vera.rnrc.dto.terror.Document;
import com.vera.rnrc.dto.terror.FL;
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

    @GetMapping("/getLatestDocument")
    public String getLatestDocument() throws FileNotFoundException, JAXBException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\bulga\\Downloads\\Tettor\\testFile.xml"));
        String body = br.lines().collect(Collectors.joining());
        StringReader reader = new StringReader(body);
        JAXBContext context = JAXBContext.newInstance(Perechen.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Perechen screen = (Perechen) unmarshaller.unmarshal(reader);

        return subjectService.selectLatestDocument(screen.getActualPerechen().getSubjects().get(0).getFl().getDocumentList()).getSeries();

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

            // Предполагается, что у вас есть метод для получения наименования субъекта
            List<Subject> subjectsList = jaxbObject.getActualPerechen().getSubjects();
            List<PhysicalPersonEntity> physicalPersonEntities = subjectsList.stream()
                    .filter(subject -> subject.getSubjectType().getName().contains("Физическое лицо"))
                    .map(this::convertToPhysicalPersonList)
                    .toList();
            List<LegalPersonEntity> legalPersonEntities = subjectsList.stream()
                    .filter(subject -> subject.getSubjectType().getName().contains("Юридическое лицо"))
                    .map(this::convertToLegalEntityList)
                    .toList();

            subjectService.saveAllLegalPerson(legalPersonEntities);
            subjectService.saveAllPhysicalPersons(physicalPersonEntities);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Ошибка при обработке файла: " + e.getMessage());
        }

        return ResponseEntity.ok("Файл успешно обработан");
    }

    private PhysicalPersonEntity convertToPhysicalPersonList(Subject subject) {
        PhysicalPersonEntity entity = new PhysicalPersonEntity();
        FL physicalPerson = subject.getFl();
        List<Document> documents = physicalPerson.getDocumentList(); // Получение списка документов
        if (documents == null || documents.isEmpty()) {
            entity.setPassportSeries("Нет данных");
            entity.setPassportNumber("Нет данных");
        } else {
            entity.setPassportSeries(subjectService.selectLatestDocument(documents).getSeries());
            entity.setPassportNumber(subjectService.selectLatestDocument(documents).getNumber());
        }
        entity.setId(subject.getSubjectId());
        entity.setInn(defaultIfNullOrEmptyOrShort(physicalPerson.getINN()));
        entity.setFullName(defaultIfNullOrEmptyOrShort(physicalPerson.getFullName()));
        entity.setSurname(defaultIfNullOrEmptyOrShort(physicalPerson.getSurname()));
        entity.setName(defaultIfNullOrEmptyOrShort(physicalPerson.getName()));
        entity.setPatronymic(defaultIfNullOrEmptyOrShort(physicalPerson.getPatronymic()));
        entity.setDateOfBirth(defaultIfNullOrEmptyOrShort(physicalPerson.getDateOfBirth()));
        entity.setPlaceOfBirth(defaultIfNullOrEmptyOrShort(physicalPerson.getPlaceOfBirth()));
        entity.setResidentSign(getResidentSign(subject.getSubjectType().getSubjectTypeId()));

        return entity;
    }

    private String getResidentSign(long subjectTypeId) {
        if (subjectTypeId == 2) {
            return "Нерезидент";
        } else if (subjectTypeId == 4) {
            return "Резидент";
        } else return "Нет данных";
    }

    private String defaultIfNullOrEmptyOrShort(String value) {
        return (value == null || value.trim().length() < 2) ? "Нет данных" : value;
    }

    private LegalPersonEntity convertToLegalEntityList(Subject subject) {
        LegalPersonEntity entity = new LegalPersonEntity();
        FL physicalPerson = subject.getFl();

        entity.setId(subject.getSubjectId());
        entity.setInn(defaultIfNullOrEmptyOrShort(physicalPerson.getINN()));
//        entity.setFullName(defaultIfNullOrEmptyOrShort(physicalPerson.getFullName()));
//        entity.setSurname(defaultIfNullOrEmptyOrShort(physicalPerson.getSurname()));
//        entity.setName(defaultIfNullOrEmptyOrShort(physicalPerson.getName()));
//        entity.setPatronymic(defaultIfNullOrEmptyOrShort(physicalPerson.getPatronymic()));
//        entity.setDateOfBirth(defaultIfNullOrEmptyOrShort(physicalPerson.getDateOfBirth()));
//        entity.setPlaceOfBirth(defaultIfNullOrEmptyOrShort(physicalPerson.getPlaceOfBirth()));
//        entity.setResidentSign(getResidentSign(subject.getSubjectType().getSubjectTypeId()));

        return entity;
    }
//    @PostMapping("/uploadXml")
//    public ResponseEntity<String> uploadXmlFile(@RequestParam("file") MultipartFile file,
//                                                @RequestParam("fileType") String type) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("Файл не предоставлен");
//        }
//
//        try {
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
//
//            if ("МВК".equals(type)) {
//                JAXBContext context = JAXBContext.newInstance(MVKDecisionListDTO.class);
//                Unmarshaller unmarshaller = context.createUnmarshaller();
//                MVKDecisionListDTO mvkDecisionListDTO = (MVKDecisionListDTO) unmarshaller.unmarshal(reader);
//                System.out.println(mvkDecisionListDTO.getActualDecisionsList().getDecisions().get(0));
//                // Обработка объекта perechen
//            } else if ("Terror".equals(type)) {
//                JAXBContext context = JAXBContext.newInstance(Perechen.class);
//                Unmarshaller unmarshaller = context.createUnmarshaller();
//                Perechen terrorList = (Perechen) unmarshaller.unmarshal(reader);
//                System.out.println(terrorList.getActualPerechen().getSubjects().get(0));
//            } else if ("ROMU".equals(type)) {
//            JAXBContext context = JAXBContext.newInstance(ConsolidatedListDTO.class);
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            ConsolidatedListDTO terrorList = (ConsolidatedListDTO) unmarshaller.unmarshal(reader);
//            System.out.println(terrorList.getIndividuals().getIndividual().get(0));
//        }else {
//                return ResponseEntity.badRequest().body("Неподдерживаемый тип файла");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError().body("Ошибка при обработке файла: " + e.getMessage());
//        }
//
//        return ResponseEntity.ok("Файл успешно обработан");
//    }

    @GetMapping("/getLegalEntityData")
    public ResponseEntity<String> getLegalEntityData(@RequestParam("entityId") String entityId,
                                                     @RequestParam("date") Optional<LocalDate> date) {
        // Логика получения данных для юридического лица.
        // Если дата не предоставлена, используется последняя доступная дата.
        LocalDate queryDate = date.orElseGet(this::getLastAvailableDate);

        // Получение данных по entityId и queryDate
        // Например, из базы данных или внутреннего сервиса

        return ResponseEntity.ok("Данные для юридического лица " + entityId + " на дату " + queryDate);
    }

    private LocalDate getLastAvailableDate() {
        // Здесь должна быть логика для определения последней доступной даты
        return LocalDate.now(); // Пример, в реальности здесь может быть запрос к базе данных
    }
}
