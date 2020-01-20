package com.app.listaprzebojow.service;

import com.app.listaprzebojow.authentication.AuthenticationFacade;
import com.app.listaprzebojow.dto.CreateSongDTO;
import com.app.listaprzebojow.dto.UserDTO;
import com.app.listaprzebojow.exception.MyException;
import com.app.listaprzebojow.mapper.CreateSongMapper;
import com.app.listaprzebojow.mapper.PlaylistMapper;
import com.app.listaprzebojow.model.*;
import com.app.listaprzebojow.model.PlaylistDTO;
import com.app.listaprzebojow.model.enums.Generation;
import com.app.listaprzebojow.repository.PlaylistRepository;
import com.app.listaprzebojow.repository.PreferenceRepository;
import com.app.listaprzebojow.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PreferenceRepository preferenceRepository;
    private final AuthenticationFacade authenticationFacade;
    private final SongService songService;
    private final PlaylistMapper playlistMapper;


    public Long addPlaylist() {
        UserDTO loggedUser = authenticationFacade.getLoggedUser();

        Playlist playlistToSave = Playlist.builder()
                .date(LocalDate.now())
                .generation(Generation.MANUAL)
                .user(User.builder().id(loggedUser.getId()).build())
                .build();

        Playlist playlistFromDb = playlistRepository.save(playlistToSave);
        return playlistFromDb.getId();
    }

    public List<PlaylistDTO> findAll() {
        return playlistRepository.findAll()
                .stream()
                .map(playlistMapper::playlistToPlaylistDTO)
                .collect(Collectors.toList());
    }

    public PlaylistDTO addSongToPlaylist(Long playlistId, Long songId) {
        if (playlistId == null){
            throw new MyException("playlistId is null");
        }
        if (songId == null){
            throw new MyException("songId is null");
        }
        Playlist playlist = playlistRepository
                .findById(playlistId)
                .orElseThrow(() -> new MyException("can't find playlist with id:" + playlistId));

        Set<Song> songs = playlist.getSongs();
        if (songs.stream().anyMatch(x -> x.getId().equals(songId))){
            throw new MyException("song with id: " + songId + " exist in your playlist");
        }

        songs.add(Song.builder().id(songId).build());
        Playlist playlistFromDb = playlistRepository.save(playlist);

        return playlistMapper.playlistToPlaylistDTO(playlistFromDb);
    }

    public PlaylistDTO generatePlaylist() {
        UserDTO loggedUser = authenticationFacade.getLoggedUser();

        Preference preference = preferenceRepository
                .findByUserId_Is(loggedUser.getId())
                .orElseThrow(() -> new MyException("First you have to complete your preferences."));

        Playlist playlistToSave = Playlist.builder()
                .date(LocalDate.now())
                .generation(Generation.AUTO)
                .user(User.builder().id(loggedUser.getId()).build())
                .songs(songService.findSongsByPreferences(preference))
                .build();

        Playlist playlistFromDb = playlistRepository.save(playlistToSave);
        return playlistMapper.playlistToPlaylistDTO(playlistFromDb);

    }
}
