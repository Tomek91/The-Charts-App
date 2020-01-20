package com.app.listaprzebojow.repository;


import com.app.listaprzebojow.model.Playlist;
import com.app.listaprzebojow.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {


}
