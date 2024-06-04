package com.gametracker.backend.user.application.update_email;

import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserNotFoundException;
import com.gametracker.backend.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UpdateEmailUserUseCase {
    private final UserRepository userRepository;

    public UpdateEmailUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UpdateEmailUserRequest request) {
        String regexPattern = "^(.+)@(\\S+)$";

        if (!emailIsValid(request, regexPattern)) {
            throw new IllegalArgumentException("Email is invalid");
        }

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UserNotFoundException(request.username()));
        user.setEmail(request.email());

        userRepository.save(user);
    }

    private static boolean emailIsValid(UpdateEmailUserRequest request, String regexPattern) {
        return Pattern.compile(regexPattern).matcher(request.email()).matches();
    }
}
