package com.app.listaprzebojow.mapper;

import com.app.listaprzebojow.dto.CreateSongDTO;
import com.app.listaprzebojow.dto.SongDTO;
import com.app.listaprzebojow.model.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateSongMapper {
    Song songDTOToSong(CreateSongDTO createSongDTO);
    CreateSongDTO songDTOToSong(Song song);
}
