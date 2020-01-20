package com.app.listaprzebojow.authentication;

import com.app.listaprzebojow.dto.UserDTO;
import com.app.listaprzebojow.exception.MyException;
import com.app.listaprzebojow.mapper.UserMapper;
import com.app.listaprzebojow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade implements IAuthenticationFacade {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UserDTO getLoggedUser(){
        return userRepository
                .findByUsername(getAuthentication().getName())
                .map(userMapper::userToUserDTO)
                .orElseThrow(() -> new MyException("USERNAME NOT FOUND"));
    }

    @Override
    public boolean isUserLoggedIn(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails;
    }
}
