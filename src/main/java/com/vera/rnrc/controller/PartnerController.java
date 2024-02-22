package com.vera.rnrc.controller;

import com.vera.rnrc.entity.PartnerLegalEntity;
import com.vera.rnrc.entity.PartnerPhysicalEntity;
import com.vera.rnrc.service.PartnerLegalService;
import com.vera.rnrc.service.PartnerPhysicalService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/partner")
public class PartnerController {
    private final PartnerLegalService partnerLegalService;
    private final PartnerPhysicalService partnerPhysicalService;
    private static final Logger logger = LogManager.getLogger("jdbc");

    @PostMapping("/addLegal")
    public ResponseEntity<String> addPartner(@RequestBody PartnerLegalEntity contractor) {
        try {
            partnerLegalService.addContractor(contractor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addPhysical")
    public ResponseEntity<String> addPartner(@RequestBody PartnerPhysicalEntity contractor) {
        try {
            partnerPhysicalService.addContractor(contractor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
