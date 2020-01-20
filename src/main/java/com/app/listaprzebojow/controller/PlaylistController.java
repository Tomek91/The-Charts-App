package com.app.listaprzebojow.controller;

import com.app.listaprzebojow.model.PlaylistDTO;
import com.app.listaprzebojow.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;

    @PostMapping("/add")
    public ResponseEntity<Long> addPlaylist(){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(playlistService.addPlaylist());
    }

    @PostMapping("/{playlist_id}/add/song/{song_id}")
    public ResponseEntity<PlaylistDTO> addSongToPlaylist(@PathVariable("playlist_id") Long playlistId,
                                                  @PathVariable("song_id") Long songId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(playlistService.addSongToPlaylist(playlistId, songId));
    }

    @GetMapping()
    public ResponseEntity<List<PlaylistDTO>> findAll(){
        return ResponseEntity.ok(playlistService.findAll());
    }

}
