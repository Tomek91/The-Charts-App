package com.app.listaprzebojow.controller;

import com.app.listaprzebojow.dto.PreferenceDTO;
import com.app.listaprzebojow.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/preferences")
public class PreferenceController {
    private final PreferenceService preferenceService;

    @PostMapping("/add")
    public ResponseEntity<Long> addPreference(@RequestBody PreferenceDTO preferenceDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(preferenceService.addPreference(preferenceDTO));
    }

    @GetMapping()
    public ResponseEntity<List<PreferenceDTO>> findAll() {
        return ResponseEntity.ok(preferenceService.findAll());
    }

}
