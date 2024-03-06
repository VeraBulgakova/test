package ru.rnrc.re2.partnercheck.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.rnrc.re2.partnercheck.repository.ResponseRepository;

@Component
@RequiredArgsConstructor
public class DatabaseCleanupTask {
    private final ResponseRepository responseRepository;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void cleanDatabaseTable() {
        responseRepository.cleanLegalPersonTable();
        responseRepository.cleanPhysicPersonTable();
    }
}
