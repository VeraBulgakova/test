package ru.rnrc.re2.partnercheck.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
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
