package ru.rnrc.re2.partnercheck;

import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PartnerCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartnerCheckApplication.class, args);
        LogManager.getLogger("jdbc").info("Application started");
    }

}
