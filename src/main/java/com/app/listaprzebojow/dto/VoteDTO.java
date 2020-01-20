package com.app.listaprzebojow.dto;

import com.app.listaprzebojow.model.Song;
import com.app.listaprzebojow.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteDTO {
    private LocalDate date;
    private UserDTO userDTO;
    private SongDTO songDTO;
}
