package com.app.listaprzebojow.dto;

import com.app.listaprzebojow.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreferenceDTO {
    private String contractor;
    private Genre genre;
    private Integer number_of_songs;
    private UserDTO userDTO;
}
