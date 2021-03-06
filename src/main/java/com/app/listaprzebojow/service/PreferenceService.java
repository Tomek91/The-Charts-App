package com.app.listaprzebojow.service;

import com.app.listaprzebojow.authentication.AuthenticationFacade;
import com.app.listaprzebojow.dto.PreferenceDTO;
import com.app.listaprzebojow.dto.UserDTO;
import com.app.listaprzebojow.exception.MyException;
import com.app.listaprzebojow.mapper.PreferenceMapper;
import com.app.listaprzebojow.model.Preference;
import com.app.listaprzebojow.model.Song;
import com.app.listaprzebojow.model.User;
import com.app.listaprzebojow.repository.PreferenceRepository;
import com.app.listaprzebojow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final PreferenceMapper preferenceMapper;


    public Long addPreference(PreferenceDTO preferenceDTO) {
        if (preferenceDTO == null) {
            throw new MyException("preferenceDTO is null");
        }
        UserDTO loggedUser = authenticationFacade.getLoggedUser();

        Preference preferenceToSave = preferenceMapper
                .preferenceDTOToPreference(preferenceDTO);

        User user = userRepository
                .findById(loggedUser.getId())
                .orElseThrow(() -> new MyException("can't find user with id: " + loggedUser.getId()));

        preferenceToSave.setUser(user);
        Preference preferenceFromDb = preferenceRepository.save(preferenceToSave);
        return preferenceFromDb.getId();
    }

    public List<PreferenceDTO> findAll() {
        return preferenceRepository.findAll()
                .stream()
                .map(preferenceMapper::preferenceToPreferenceDTO)
                .collect(Collectors.toList());
    }
}
