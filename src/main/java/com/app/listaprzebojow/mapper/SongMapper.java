package com.app.listaprzebojow.mapper;

import com.app.listaprzebojow.dto.SongDTO;
import com.app.listaprzebojow.dto.VoteDTO;
import com.app.listaprzebojow.model.Song;
import com.app.listaprzebojow.model.Vote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {
    SongDTO songToSongDTO(Song song);
    Song songDTOToSong(SongDTO songDTO);
}
