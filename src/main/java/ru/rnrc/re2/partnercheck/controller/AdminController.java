package ru.rnrc.re2.partnercheck.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rnrc.re2.partnercheck.dto.authorization.AuthorityDTO;
import ru.rnrc.re2.partnercheck.dto.authorization.UserDTO;
import ru.rnrc.re2.partnercheck.entity.User;
import ru.rnrc.re2.partnercheck.service.AdminService;


@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class AdminController {

    private final AdminService service;

    @PostMapping(value = "/user")
    public User createUser(@RequestBody UserDTO userDTO) {
        return service.createUser(userDTO);
    }

    @PutMapping(value = "/user/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody UserDTO user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/user/{id}")
    public void softDeleteUser(@PathVariable("id") Long id) {
        service.softDeleteUser(id);
    }

    @PostMapping(value = "/authority")
    public AuthorityDTO addAuthority(@RequestBody AuthorityDTO authorityDTO) {
        return service.addAuthority(authorityDTO);
    }
}
