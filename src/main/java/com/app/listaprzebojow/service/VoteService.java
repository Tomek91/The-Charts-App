package com.app.listaprzebojow.service;

import com.app.listaprzebojow.authentication.AuthenticationFacade;
import com.app.listaprzebojow.dto.BestSongDTO;
import com.app.listaprzebojow.dto.SongDTO;
import com.app.listaprzebojow.dto.UserDTO;
import com.app.listaprzebojow.dto.VoteSongDTO;
import com.app.listaprzebojow.exception.MyException;
import com.app.listaprzebojow.mapper.SongMapper;
import com.app.listaprzebojow.model.Song;
import com.app.listaprzebojow.model.User;
import com.app.listaprzebojow.model.Vote;
import com.app.listaprzebojow.model.enums.Genre;
import com.app.listaprzebojow.repository.SongRepository;
import com.app.listaprzebojow.repository.UserRepository;
import com.app.listaprzebojow.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;


    public List<BestSongDTO> findBest() {
        return groupBySongs(voteRepository.findAll());
    }

    public List<BestSongDTO> findBestInADay(LocalDate date) {
        if (date == null) {
            throw new MyException("date is null");
        }
        List<BestSongDTO> bestSongInADay = groupBySongs(voteRepository.findAllByDate(date));

        if (bestSongInADay.isEmpty()) {
            throw new MyException("Nobody voted in a day: " + date.toString());
        } else {
            return bestSongInADay;
        }
    }

    public List<BestSongDTO> findBestByGenre(Genre genre) {
        if (genre == null) {
            throw new MyException("genre is null");
        }
        List<BestSongDTO> bestSongInADay = groupBySongs(voteRepository.findAllByGenre(genre));


        if (bestSongInADay.isEmpty()) {
            throw new MyException("Nobody voted in genre songs: " + genre.toString());
        } else {
            return bestSongInADay;
        }
    }

    private List<BestSongDTO> groupBySongs(List<Vote> votes) {
        return votes
                .stream()
                .collect(Collectors.groupingBy(Vote::getSong, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .map(e -> BestSongDTO.builder()
                        .contractor(e.getKey().getContractor())
                        .genre(e.getKey().getGenre())
                        .title(e.getKey().getTitle())
                        .votes(e.getValue())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public Long addVote(VoteSongDTO voteSongDTO) {
        if (voteSongDTO == null) {
            throw new MyException("voteSongDTO is null");
        }

        UserDTO loggedUser = authenticationFacade.getLoggedUser();
        Song song = songRepository
                .findById(voteSongDTO.getSongId())
                .orElseThrow(() -> new MyException("can't find song with id: " + voteSongDTO.getSongId()));
        User user = userRepository
                .findById(loggedUser.getId())
                .orElseThrow(() -> new MyException("can't find user with id: " + loggedUser.getId()));


        Vote voteToSave = Vote.builder()
                .date(LocalDate.now())
                .song(song)
                .user(user)
                .build();

        Vote voteFromDb = voteRepository.save(voteToSave);
        return voteFromDb.getId();
    }
}
