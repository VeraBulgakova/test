package com.vera.rnrc.service;

import com.vera.rnrc.dto.mvk.MVKPerechenDTO;

public interface MVKService {
    void saveAll(MVKPerechenDTO jaxbObject, String finalFileName, String type);

}
