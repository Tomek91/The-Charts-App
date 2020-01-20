package com.app.listaprzebojow.repository;


import com.app.listaprzebojow.model.Song;
import com.app.listaprzebojow.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {


}
