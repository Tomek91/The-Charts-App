package com.app.listaprzebojow.mapper;

import com.app.listaprzebojow.dto.PlaylistDTO;
import com.app.listaprzebojow.model.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
    @Mappings({
            @Mapping(source = "user", target = "userDTO")
    })
    PlaylistDTO playlistToPlaylistDTO(Playlist playlist);

    @Mappings({
            @Mapping(source = "userDTO", target = "user")
    })
    Playlist playlistDTOToPlaylist(PlaylistDTO playlistDTO);
}
