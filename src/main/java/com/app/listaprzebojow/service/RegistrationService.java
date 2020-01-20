package com.app.listaprzebojow.service;

import com.app.listaprzebojow.dto.UserDTO;
import com.app.listaprzebojow.exception.MyException;
import com.app.listaprzebojow.mapper.UserMapper;
import com.app.listaprzebojow.model.Role;
import com.app.listaprzebojow.model.User;
import com.app.listaprzebojow.repository.RoleRepository;
import com.app.listaprzebojow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public Long registerNewUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new MyException("USER OBJECT IS NULL");
        }
        if (userDTO.getRoleDTO() == null) {
            throw new MyException("ROLE OBJECT IS NULL");
        }
        if (userDTO.getRoleDTO().getName() == null) {
            throw new MyException("ROLE NAME IS NULL");
        }

        Role role = roleRepository
                .findByName(userDTO.getRoleDTO().getName())
                .orElseThrow(NullPointerException::new);

        User user = userMapper.userDTOToUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(role);
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setUsername(userDTO.getUsername());
        user.setActive(true);

        User userFromDb = userRepository.save(user);
        return userFromDb.getId();
    }
}
