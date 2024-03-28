package ru.rnrc.re2.partnercheck;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER, name = "basicAuth", scheme = "basic")
@OpenAPIDefinition(info = @Info(title = "Parser", version = "1.0.0"))
public class PartnerCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartnerCheckApplication.class, args);
    }

}
