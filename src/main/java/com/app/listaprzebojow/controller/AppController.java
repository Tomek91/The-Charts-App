package com.app.listaprzebojow.controller;

import com.app.listaprzebojow.dto.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppController {


    @GetMapping("/accessDenied")
    public ResponseEntity<Info<String>> accessDenied() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Info.<String>builder()
                        .error("Access denied")
                        .httpStatusCode(HttpStatus.FORBIDDEN)
                        .build()
                );
    }

}
