package com.app.listaprzebojow.dto;

import com.app.listaprzebojow.model.enums.Generation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistDTO {
    private LocalDate date;
    private Generation generation;
    private UserDataDTO userDTO;
    private Set<SongDTO> songs;
}
