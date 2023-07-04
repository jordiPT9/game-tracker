package com.gametracker.backend.auth.application;

import com.gametracker.backend.auth.domain.UnauthorizedException;
import com.gametracker.backend.security.infrastructure.configuration.JwtService;
import com.gametracker.backend.auth.infrastructure.entrypoints.AuthenticateUserResponse;
import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthenticateUserUseCaseImpl(AuthenticationManager authenticationManager, UserRepository userRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public AuthenticateUserResponse execute(AuthenticateUserCommand command) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(command.username(), command.password()));
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid credentials");
        }

        final User user = userRepository.findByUsername(command.username());
        final String jwt = jwtService.generateToken(user);

        return new AuthenticateUserResponse(jwt);
    }
}
