package ru.rnrc.re2.partnercheck.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Create user",
            description = "This method creates a new user using the data provided in the request body." +
                    "You must pass valid user data in JSON format.")
    @PostMapping(value = "/user")
    public User createUser(@RequestBody UserDTO userDTO) {
        return service.createUser(userDTO);
    }

    @Operation(summary = "Update user",
            description = "This method update a user using the data provided in the request body." +
                    "You must pass valid user data in JSON format.")
    @PutMapping(value = "/user/{id}")
    public User updateUser(@Parameter(
            name = "id",
            description = "ID of the user to be updated",
            example = "2",
            required = true) @PathVariable("id") Long id, @RequestBody UserDTO user) {
        return service.updateUser(id, user);
    }

    @Operation(summary = "Soft delete user",
            description = "Performs a soft deletion of a user identified by their unique ID." +
                    "Instead of completely deleting the database, a soft delete usually marks the user" +
                    "as inactive, allowing its data to be saved for future use or recovery.")
    @DeleteMapping("/user/{id}")
    public void softDeleteUser(@Parameter(
            name = "id",
            description = "ID of the user to be deleted",
            example = "2",
            required = true) @PathVariable("id") Long id) {
        service.softDeleteUser(id);
    }

    @Operation(summary = "Add authority",
            description = "This method creates a new authority based on the information provided." +
                    "You must send a JSON describing the permission that will be added to the system.")
    @PostMapping(value = "/authority")
    public AuthorityDTO addAuthority(@RequestBody AuthorityDTO authorityDTO) {
        return service.addAuthority(authorityDTO);
    }
}
