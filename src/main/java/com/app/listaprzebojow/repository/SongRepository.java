package com.app.listaprzebojow.repository;


import com.app.listaprzebojow.model.Song;
import com.app.listaprzebojow.model.Vote;
import com.app.listaprzebojow.model.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("select s from Song s where s.contractor=:contractor or s.genre=:genre")
    Set<Song> findSongsByPreferences(@Param("contractor") String contractor, @Param("genre") Genre genre);
}
