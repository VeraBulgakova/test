package com.vera.rnrc.controller;
import com.vera.rnrc.dto.mvk.MVKDecisionListDTO;
import com.vera.rnrc.dto.romu.ConsolidatedListDTO;
import com.vera.rnrc.dto.terror.Perechen;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ParserController {
    public void terror() throws JAXBException, FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\bulga\\Downloads\\testFile.xml"));
        String body = br.lines().collect(Collectors.joining());
        StringReader reader = new StringReader(body);
        JAXBContext context = JAXBContext.newInstance(Perechen.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Perechen screen = (Perechen) unmarshaller.unmarshal(reader);
        System.out.println(screen.getActualPerechen().getSubjects().get(0));
        System.out.println(screen.getLatestExcluded().getIncludedExcludedEntities().get(0));
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

            if ("МВК".equals(type)) {
                JAXBContext context = JAXBContext.newInstance(MVKDecisionListDTO.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                MVKDecisionListDTO mvkDecisionListDTO = (MVKDecisionListDTO) unmarshaller.unmarshal(reader);
                System.out.println(mvkDecisionListDTO.getActualDecisionsList().getDecisions().get(0));
                // Обработка объекта perechen
            } else if ("Terror".equals(type)) {
                JAXBContext context = JAXBContext.newInstance(Perechen.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Perechen terrorList = (Perechen) unmarshaller.unmarshal(reader);
                System.out.println(terrorList.getActualPerechen().getSubjects().get(0));
            } else if ("ROMU".equals(type)) {
            JAXBContext context = JAXBContext.newInstance(ConsolidatedListDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ConsolidatedListDTO terrorList = (ConsolidatedListDTO) unmarshaller.unmarshal(reader);
            System.out.println(terrorList.getIndividuals().getIndividual().get(0));
        }else {
                return ResponseEntity.badRequest().body("Неподдерживаемый тип файла");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Ошибка при обработке файла: " + e.getMessage());
        }

        return ResponseEntity.ok("Файл успешно обработан");
    }
//    @PostMapping("/uploadXml")
//    public ResponseEntity<String> uploadXmlFile(@RequestParam("file") MultipartFile file,@RequestParam("fileType")String type) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("Файл не предоставлен");
//        }
//
//        try {
//            // Получение содержимого файла
//            //write parser xml file using jsoup
//            String content = new String(file.getBytes());
//
////limit content to 1000 symbols
//            if (content.length() > 2000) {
//                content = content.substring(0, 4000);
//            }
//            System.out.println(content);
//
//            // Здесь может быть логика обработки XML-файла
//
//            return ResponseEntity.ok("Файл успешно загружен и обработан");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Ошибка при обработке файла");
//        }
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
