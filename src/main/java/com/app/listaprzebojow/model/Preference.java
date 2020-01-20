package com.app.listaprzebojow.model;

import com.app.listaprzebojow.model.enums.Genre;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "preferences")
public class Preference {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String contractor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(nullable = false)
    private Integer number_of_songs;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn (name = "user_id", unique = true)
    private User user;
}
