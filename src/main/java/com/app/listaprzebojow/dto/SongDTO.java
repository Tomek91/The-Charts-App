package com.app.listaprzebojow.dto;

import com.app.listaprzebojow.model.enums.Genre;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongDTO {
    private String title;
    private String contractor;
    private Genre genre;
}
