package com.vera.rnrc.controller;

import com.vera.rnrc.dto.authorization.AuthorityDTO;
import com.vera.rnrc.dto.authorization.UserDTO;
import com.vera.rnrc.entity.UserEntity;
import com.vera.rnrc.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService service;

    @PostMapping(value = "/createUser")
    public UserEntity createUser(@RequestBody UserDTO userDTO) {
        return service.createUser(userDTO);
    }

    @PutMapping(value = "/updateUser")
    public UserEntity updateUser(@RequestBody UserDTO user) {
        return service.updateUser(user);
    }

    @PostMapping("/softDeleteUser/{id}")
    public UserDTO softDeleteUser(@PathVariable("id") Integer id) {
        return service.softDeleteUser(id);
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        service.deleteUser(id);
    }

    @PostMapping(value = "/addAuthority")
    public AuthorityDTO addAuthority(@RequestBody AuthorityDTO authorityDTO) {
        return service.addAuthority(authorityDTO);
    }
}
