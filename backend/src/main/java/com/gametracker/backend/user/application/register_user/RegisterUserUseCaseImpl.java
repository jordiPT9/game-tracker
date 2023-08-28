package com.gametracker.backend.user.application.register_user;

import com.gametracker.backend.role.domain.RoleName;
import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {
    private final UserRepository userRepository;

    public RegisterUserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(RegisterUserCommand command) {
        User user = User.builder()
                .id(command.id())
                .username(command.username())
                .password(command.password())
                .email(command.email())
                .role(RoleName.valueOf(command.role()))
                .build();

        userRepository.save(user);
    }
}
