package com.gametracker.backend.libraryGame.infrastructure.persistence;

import com.gametracker.backend.libraryGame.domain.LibraryGame;
import com.gametracker.backend.user.domain.UserNotFound;
import com.gametracker.backend.user.infrastructure.persistence.UserJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class LibraryGameMapper {
    private final UserJpaRepository userJpaRepository;

    public LibraryGameMapper(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public LibraryGameJpaEntity mapToJpaEntity(LibraryGame libraryGame) {
        String username = libraryGame.getUsername();

        return new LibraryGameJpaEntity(
                libraryGame.getId(),
                libraryGame.getTitle(),
                libraryGame.getRating(),
                libraryGame.getStatus(),
                userJpaRepository.findByUsername(username).orElseThrow(() -> new UserNotFound(username))
        );
    }

    public LibraryGame mapToDomainEntity(LibraryGameJpaEntity libraryGameJpaEntity) {
        return new LibraryGame(
                libraryGameJpaEntity.getId(),
                libraryGameJpaEntity.getTitle(),
                libraryGameJpaEntity.getRating(),
                libraryGameJpaEntity.getStatus(),
                libraryGameJpaEntity.getUser().getUsername()
        );
    }
}
