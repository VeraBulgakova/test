package ru.rnrc.re2.partnercheck.scheduler;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.rnrc.re2.partnercheck.repository.ResponseRepository;

@Component
@RequiredArgsConstructor
public class DatabaseCleanupTask {
    private final ResponseRepository responseRepository;
    private static final Logger businessLogger = LogManager.getLogger("jdbc");

    @Scheduled(cron = "0 0 0 1 1/2 ?")
    public void cleanDatabaseTable() {
        responseRepository.cleanLegalPersonTable();
        responseRepository.cleanPhysicPersonTable();
        businessLogger.info("Очищены таблицы содержащие перечни проверки от устаревших записей.");
    }
}
