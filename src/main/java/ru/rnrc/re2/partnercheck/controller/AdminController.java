package ru.rnrc.re2.partnercheck.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rnrc.re2.partnercheck.dto.authorization.AuthorityDTO;
import ru.rnrc.re2.partnercheck.dto.authorization.UserDTO;
import ru.rnrc.re2.partnercheck.entity.User;
import ru.rnrc.re2.partnercheck.service.AdminService;


@RestController
@RequestMapping("/admin/v1")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    @PostMapping(value = "/user")
    public User createUser(@RequestBody UserDTO userDTO) {
        return service.createUser(userDTO);
    }

    @PutMapping(value = "/user/{id}")
    public User updateUser(@RequestBody UserDTO user) {
        return service.updateUser(user);
    }

    @DeleteMapping("/user/{id}")
    public UserDTO softDeleteUser(@PathVariable("id") Integer id) {
        return service.softDeleteUser(id);
    }

    @PostMapping(value = "/authority")
    public AuthorityDTO addAuthority(@RequestBody AuthorityDTO authorityDTO) {
        return service.addAuthority(authorityDTO);
    }
}
