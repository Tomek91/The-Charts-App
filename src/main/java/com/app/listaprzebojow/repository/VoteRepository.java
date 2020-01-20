package com.app.listaprzebojow.repository;


import com.app.listaprzebojow.model.Vote;
import com.app.listaprzebojow.model.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByDate(LocalDate date);

    @Query("select v from Vote v join v.song s where s.genre=:genre")
    List<Vote> findAllByGenre(Genre genre);
}
