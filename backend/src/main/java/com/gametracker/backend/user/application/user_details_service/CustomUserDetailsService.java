package com.gametracker.backend.user.application.user_details_service;

import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserNotFoundException;
import com.gametracker.backend.user.domain.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UserNotFoundException(username));

        return createUserDetails(user);
    }

    private static org.springframework.security.core.userdetails.User createUserDetails(User user) {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }
}
