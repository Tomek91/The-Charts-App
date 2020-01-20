package com.app.listaprzebojow.service;

import com.app.listaprzebojow.exception.MyException;
import com.app.listaprzebojow.model.Role;
import com.app.listaprzebojow.model.User;
import com.app.listaprzebojow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Qualifier("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new MyException("USERNAME NOT FOUND"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true, true, true, true,
                getRoles(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> getRoles(Role role) {
        return Stream.of(role).map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toSet());
    }


}
