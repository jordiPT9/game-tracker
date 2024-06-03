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

  public void execute(RegisterUserRequest request) {
    User user =
        User.builder()
            .id(request.id())
            .username(request.username())
            .password(request.password())
            .email(request.email())
            .role(RoleName.valueOf(request.role()))
            .build();

    userRepository.save(user);
  }
}
