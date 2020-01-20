package com.app.listaprzebojow.controller;

import com.app.listaprzebojow.dto.CreateSongDTO;
import com.app.listaprzebojow.repository.SongRepository;
import com.app.listaprzebojow.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Long> addSong(@RequestBody CreateSongDTO createSongDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(songService.addSong(createSongDTO));
    }

    @GetMapping()
    public ResponseEntity<List<CreateSongDTO>> findAll(){
        return ResponseEntity.ok(songService.findAll());
    }

}
