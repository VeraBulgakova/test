package com.vera.rnrc.service;

import com.vera.rnrc.dto.romu.ROMUPerechenDTO;

public interface ROMUService {
    void saveAll(ROMUPerechenDTO jaxbObject, String finalFileName, String type);

}
