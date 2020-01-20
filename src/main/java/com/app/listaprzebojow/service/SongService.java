package com.app.listaprzebojow.service;

import com.app.listaprzebojow.dto.CreateSongDTO;
import com.app.listaprzebojow.dto.SongDTO;
import com.app.listaprzebojow.exception.MyException;
import com.app.listaprzebojow.mapper.CreateSongMapper;
import com.app.listaprzebojow.model.Song;
import com.app.listaprzebojow.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final CreateSongMapper createSongMapper;


    public Long addSong(CreateSongDTO createSongDTO){
        if (createSongDTO == null){
            throw new MyException("createSongDTO is null");
        }

        Song songToSave = createSongMapper.songDTOToSong(createSongDTO);
        return songToSave.getId();
    }

    public List<CreateSongDTO> findAll(){

        return songRepository.findAll()
                .stream()
                .map(createSongMapper::songDTOToSong)
                .collect(Collectors.toList());
    }
}
