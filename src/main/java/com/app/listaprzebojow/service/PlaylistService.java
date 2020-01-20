package com.app.listaprzebojow.service;

import com.app.listaprzebojow.authentication.AuthenticationFacade;
import com.app.listaprzebojow.dto.PlaylistDTO;
import com.app.listaprzebojow.dto.UserDTO;
import com.app.listaprzebojow.exception.MyException;
import com.app.listaprzebojow.mapper.PlaylistMapper;
import com.app.listaprzebojow.model.Playlist;
import com.app.listaprzebojow.model.Preference;
import com.app.listaprzebojow.model.Song;
import com.app.listaprzebojow.model.User;
import com.app.listaprzebojow.model.enums.Generation;
import com.app.listaprzebojow.repository.PlaylistRepository;
import com.app.listaprzebojow.repository.PreferenceRepository;
import com.app.listaprzebojow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final UserRepository userRepository;


    public Long addPlaylist() {
        UserDTO loggedUser = authenticationFacade.getLoggedUser();
        User user = userRepository
                .findById(loggedUser.getId())
                .orElseThrow(() -> new MyException("can't find user with id: " + loggedUser.getId()));

        Playlist playlistToSave = Playlist.builder()
                .date(LocalDate.now())
                .generation(Generation.MANUAL)
                .user(user)
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
        if (playlistId == null) {
            throw new MyException("playlistId is null");
        }
        if (songId == null) {
            throw new MyException("songId is null");
        }
        Playlist playlist = playlistRepository
                .findById(playlistId)
                .orElseThrow(() -> new MyException("can't find playlist with id:" + playlistId));

        Set<Song> songs = playlist.getSongs();
        if (songs.stream().anyMatch(x -> x.getId().equals(songId))) {
            throw new MyException("song with id: " + songId + " exist in your playlist");
        }

        songs.add(Song.builder().id(songId).build());
        Playlist playlistFromDb = playlistRepository.save(playlist);

        return playlistMapper.playlistToPlaylistDTO(playlistFromDb);
    }

    public PlaylistDTO generatePlaylist() {
        UserDTO loggedUser = authenticationFacade.getLoggedUser();
        User user = userRepository
                .findById(loggedUser.getId())
                .orElseThrow(() -> new MyException("can't find user with id: " + loggedUser.getId()));

        Preference preference = preferenceRepository
                .findByUserId_Is(loggedUser.getId())
                .orElseThrow(() -> new MyException("First you have to complete your preferences."));

        Playlist playlistToSave = Playlist.builder()
                .date(LocalDate.now())
                .generation(Generation.AUTO)
                .user(user)
                .songs(songService.findSongsByPreferences(preference))
                .build();

        Playlist playlistFromDb = playlistRepository.save(playlistToSave);
        return playlistMapper.playlistToPlaylistDTO(playlistFromDb);

    }

    public Long delete(Long id) {
        if (id == null) {
            throw new MyException("id is null");
        }

        Playlist playlistToDelete = playlistRepository.findById(id)
                .orElseThrow(() -> new MyException("no team with id " + id));
        playlistRepository.delete(playlistToDelete);
        return playlistToDelete.getId();
    }
}
