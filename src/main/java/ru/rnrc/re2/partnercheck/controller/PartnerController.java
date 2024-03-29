package ru.rnrc.re2.partnercheck.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rnrc.re2.partnercheck.dto.PartnerDTO;
import ru.rnrc.re2.partnercheck.service.PartnerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@SecurityRequirement(name = "basicAuth")
public class PartnerController {
    private final PartnerService partnerLegalService;
    private static final Logger errorLogger = LoggerFactory.getLogger(PartnerController.class);

    @Operation(summary = "Add partner",
            description = "This endpoint accepts information about the partner and all its associated entities and stores it in the system.")
    @PostMapping("/partner")
    public ResponseEntity<String> addPartner(@RequestBody PartnerDTO contractor) {
        try {
            partnerLegalService.add(contractor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            errorLogger.error("Ошибка при добавлении юридического лица: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
