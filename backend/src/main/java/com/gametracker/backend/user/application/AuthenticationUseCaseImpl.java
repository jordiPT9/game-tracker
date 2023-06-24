package com.gametracker.backend.user.application;

import com.gametracker.backend.security.jwt.JwtUtil;
import com.gametracker.backend.user.domain.BadCredentials;
import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserNotFound;
import com.gametracker.backend.user.domain.UserRepository;
import com.gametracker.backend.user.infrastructure.entrypoints.Authentication.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUseCaseImpl implements AuthenticationUseCase {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthenticationUseCaseImpl(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthenticationResponse execute(AuthenticationCommand command) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(command.username(), command.password()));
        } catch (BadCredentialsException ex) {
            throw new BadCredentials("Incorrect username or password" + ex.getMessage());
        }

        final User user = userRepository.findByUsername(command.username());

        if (user == null) {
            throw new UserNotFound(command.username());
        }

        final String jwt = jwtUtil.generateToken(user);

        return new AuthenticationResponse(jwt);
    }
}
