package com.gametracker.backend.user.application;

import com.gametracker.backend.role.domain.RoleName;
import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCaseImpl implements RegisterUserUseCase{
    private final UserRepository userRepository;

    public RegisterUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(RegisterUserCommand command) {
        User user = new User(
                command.id(),
                command.username(),
                command.password(),
                command.email(),
                RoleName.valueOf(command.role())
        );

        userRepository.save(user);
    }
}
