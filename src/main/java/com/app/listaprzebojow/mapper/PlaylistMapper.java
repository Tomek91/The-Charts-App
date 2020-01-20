package com.app.listaprzebojow.mapper;

import com.app.listaprzebojow.model.Playlist;
import com.app.listaprzebojow.model.PlaylistDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
    PlaylistDTO playlistToPlaylistDTO(Playlist playlist);

    Playlist playlistDTOToPlaylist(PlaylistDTO playlistDTO);
}
