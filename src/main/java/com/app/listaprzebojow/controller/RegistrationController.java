package com.app.listaprzebojow.controller;

import com.app.listaprzebojow.dto.UserDTO;
import com.app.listaprzebojow.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping("/new")
    public ResponseEntity<Long> addUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registrationService.registerNewUser(userDTO));
    }
}
