package ru.rnrc.re2.partnercheck.service;

import ru.rnrc.re2.partnercheck.dto.terror.TERRORPerechenDTO;

public interface TerrorService {
    void saveAll(TERRORPerechenDTO jaxbObject, String finalFileName, String type);
}


