package ru.rnrc.re2.partnercheck.service;

import ru.rnrc.re2.partnercheck.dto.romu.ROMUPerechenDTO;

public interface ROMUService {
    void saveAll(ROMUPerechenDTO jaxbObject, String finalFileName, String type);

}
