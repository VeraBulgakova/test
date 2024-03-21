package ru.rnrc.re2.partnercheck.dto.authorization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    @Id
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private String email;
    private Boolean isDeleted;
}