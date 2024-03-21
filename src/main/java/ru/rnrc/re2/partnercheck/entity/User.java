package ru.rnrc.re2.partnercheck.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "email")
    private String email;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
