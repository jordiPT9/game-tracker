package com.gametracker.backend.auth.application;

import com.gametracker.backend.auth.domain.UnauthorizedException;
import com.gametracker.backend.security.JWTUtil;
import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserNotFoundException;
import com.gametracker.backend.user.domain.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTUtil JWTUtil;

    public AuthenticateUserUseCaseImpl(AuthenticationManager authenticationManager, UserRepository userRepository, JWTUtil JWTUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.JWTUtil = JWTUtil;
    }

    @Override
    public AuthenticateUserResponse execute(AuthenticateUserRequest request) {
        final User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UserNotFoundException(request.username()));

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid credentials");
        }

        final String jwt = JWTUtil.issueToken(user.getUsername());

        return new AuthenticateUserResponse(jwt);
    }
}
