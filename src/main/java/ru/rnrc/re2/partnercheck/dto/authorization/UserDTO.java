package ru.rnrc.re2.partnercheck.dto.authorization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    @Id
    @Schema(type = "integer", example = "2")
    private Long id;
    @Schema(type = "string", example = "user")
    private String username;
    @Schema(type = "string", example = "user")
    private String password;
    @Schema(type = "boolean", example = "true")
    private Boolean enabled;
    @Schema(type = "string", example = "user@mail.ru")
    private String email;
    @Schema(type = "boolean", example = "false")
    private Boolean isDeleted;
}