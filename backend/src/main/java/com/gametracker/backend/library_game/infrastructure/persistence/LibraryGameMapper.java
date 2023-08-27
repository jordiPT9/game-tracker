package com.gametracker.backend.library_game.infrastructure.persistence;

import com.gametracker.backend.library_game.domain.LibraryGame;
import com.gametracker.backend.user.domain.UserNotFoundException;
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
                userJpaRepository.findByUsername(username)
        );
    }

    public LibraryGame mapToDomainEntity(LibraryGameJpaEntity libraryGameJpaEntity) {
        return LibraryGame.builder()
                .id(libraryGameJpaEntity.getId())
                .title(libraryGameJpaEntity.getTitle())
                .rating(libraryGameJpaEntity.getRating())
                .status(libraryGameJpaEntity.getStatus())
                .username(libraryGameJpaEntity.getUser().getUsername())
                .build();
    }
}
