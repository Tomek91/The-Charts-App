package com.app.listaprzebojow.controller;

import com.app.listaprzebojow.dto.BestSongDTO;
import com.app.listaprzebojow.dto.SongDTO;
import com.app.listaprzebojow.dto.VoteSongDTO;
import com.app.listaprzebojow.model.enums.Genre;
import com.app.listaprzebojow.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Long> addVote(@RequestBody VoteSongDTO voteSongDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(voteService.addVote(voteSongDTO));
    }

    @GetMapping("/best")
    public ResponseEntity<List<BestSongDTO>> findBest() {
        return ResponseEntity.ok(voteService.findBest());
    }

    @GetMapping("/best-by-genre/{genre}")
    public ResponseEntity<List<BestSongDTO>> findBestByGenre(@PathVariable Genre genre) {
        return ResponseEntity.ok(voteService.findBestByGenre(genre));
    }

    @GetMapping("/best-in-a-day/{date}")
    public ResponseEntity<List<BestSongDTO>> findBestInADay(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(voteService.findBestInADay(date));
    }
}
