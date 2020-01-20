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
public class CreateSongDTO {
    private String title;
    private String contractor;
    private Genre genre;
}
