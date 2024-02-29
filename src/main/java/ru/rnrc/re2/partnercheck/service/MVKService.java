package ru.rnrc.re2.partnercheck.service;

import ru.rnrc.re2.partnercheck.dto.mvk.MVKPerechenDTO;

public interface MVKService {
    void saveAll(MVKPerechenDTO jaxbObject, String finalFileName, String type);

}
