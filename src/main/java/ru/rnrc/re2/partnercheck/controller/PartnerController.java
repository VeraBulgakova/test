package ru.rnrc.re2.partnercheck.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rnrc.re2.partnercheck.entity.PartnerLegal;
import ru.rnrc.re2.partnercheck.entity.PartnerPhysical;
import ru.rnrc.re2.partnercheck.service.PartnerLegalService;
import ru.rnrc.re2.partnercheck.service.PartnerPhysicalService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PartnerController {
    private final PartnerLegalService partnerLegalService;
    private final PartnerPhysicalService partnerPhysicalService;
    private static final Logger logger = LogManager.getLogger("jdbc");

    @PostMapping("/partner/legal")
    public ResponseEntity<String> addPartnerLegal(@RequestBody PartnerLegal contractor) {
        try {
            partnerLegalService.addContractor(contractor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/partner/physical")
    public ResponseEntity<String> addPartnerPhysical(@RequestBody PartnerPhysical contractor) {
        try {
            partnerPhysicalService.addContractor(contractor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
