package com.gametracker.backend.user.application.register_user;

import com.gametracker.backend.role.domain.RoleName;
import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {
  private final UserRepository userRepository;

  public RegisterUserUseCase(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void execute(RegisterUserCommand command) {
    User user =
        User.builder()
            .id(command.id())
            .username(command.username())
            .password(command.password())
            .email(command.email())
            .role(RoleName.valueOf(command.role()))
            .build();

    userRepository.save(user);
  }
}
