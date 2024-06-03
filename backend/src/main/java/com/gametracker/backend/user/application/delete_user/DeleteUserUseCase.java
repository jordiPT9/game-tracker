package com.gametracker.backend.user.application.delete_user;

import com.gametracker.backend.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserUseCase {
    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(DeleteUserRequest request) {
        userRepository.deleteById(request.userId());
    }
}
