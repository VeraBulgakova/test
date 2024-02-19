package com.vera.rnrc.service;

import com.vera.rnrc.dto.terror.TERRORPerechenDTO;

public interface TerrorService {
    void saveAll(TERRORPerechenDTO jaxbObject, String finalFileName, String type);
}


