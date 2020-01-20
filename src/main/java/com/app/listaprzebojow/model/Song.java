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
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contractor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "song", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Vote> votes;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "songs")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Playlist> playlists;

}
