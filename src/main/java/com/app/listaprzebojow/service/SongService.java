package com.app.listaprzebojow.service;

import com.app.listaprzebojow.dto.CreateSongDTO;
import com.app.listaprzebojow.exception.MyException;
import com.app.listaprzebojow.mapper.CreateSongMapper;
import com.app.listaprzebojow.model.Preference;
import com.app.listaprzebojow.model.Song;
import com.app.listaprzebojow.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final CreateSongMapper createSongMapper;


    public Long addSong(CreateSongDTO createSongDTO) {
        if (createSongDTO == null) {
            throw new MyException("createSongDTO is null");
        }

        Song songToSave = createSongMapper.songDTOToSong(createSongDTO);
        Song songFromDb = songRepository.save(songToSave);
        return songFromDb.getId();
    }

    public List<CreateSongDTO> findAll() {

        return songRepository.findAll()
                .stream()
                .map(createSongMapper::songDTOToSong)
                .collect(Collectors.toList());
    }

    public Set<Song> findSongsByPreferences(Preference preference) {
        if (preference == null) {
            throw new MyException("preference is null");
        }
        return songRepository.findSongsByPreferences(preference.getContractor(), preference.getGenre())
                .stream()
                .sorted(Comparator.comparing(x -> x.getVotes().size(), Comparator.reverseOrder()))
                .limit(preference.getNumber_of_songs())
                .collect(Collectors.toSet());
    }
}
