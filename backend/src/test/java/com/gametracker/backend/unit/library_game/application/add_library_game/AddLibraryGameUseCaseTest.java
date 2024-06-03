package com.gametracker.backend.unit.library_game.application.add_library_game;

import com.gametracker.backend.game.domain.Game;
import com.gametracker.backend.game.domain.GameDoesNotExistException;
import com.gametracker.backend.game.domain.GameRepository;
import com.gametracker.backend.library_game.application.add_library_game.AddLibraryGameRequest;
import com.gametracker.backend.library_game.application.add_library_game.AddLibraryGameUseCase;
import com.gametracker.backend.library_game.domain.LibraryGame;
import com.gametracker.backend.library_game.domain.LibraryGameAlreadyAddedException;
import com.gametracker.backend.library_game.domain.LibraryGameRepository;
import com.gametracker.backend.library_game.domain.LibraryGameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddLibraryGameUseCaseTest {

    @Mock
    private LibraryGameRepository libraryGameRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private AddLibraryGameUseCase addLibraryGameUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldAddLibraryGame_WhenGameExistsAndNotAlreadyAdded() {
        // Arrange
        Game game = Game.builder()
                .title("Test Game")
                .build();
        AddLibraryGameRequest request = new AddLibraryGameRequest(
                "1",
                "Test Game",
                4.5,
                LibraryGameStatus.PLAYED,
                "user123"
        );
        when(gameRepository.findGame("Test Game"))
                .thenReturn(Optional.of(game));
        when(libraryGameRepository.findByTitleAndUsername("Test Game", "user123"))
                .thenReturn(null);

        // Act
        addLibraryGameUseCase.execute(request);

        // Assert
        verify(libraryGameRepository, times(1)).save(any(LibraryGame.class));
    }

    @Test
    void execute_ShouldThrowGameDoesNotExistException_WhenGameDoesNotExist() {
        // Arrange
        AddLibraryGameRequest request = new AddLibraryGameRequest(
                "1",
                "Nonexistent Game",
                4.5,
                LibraryGameStatus.PLAYED,
                "user123"
        );
        when(gameRepository.findGame("Nonexistent Game"))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(GameDoesNotExistException.class, () -> addLibraryGameUseCase.execute(request));
        verify(libraryGameRepository, never()).save(any(LibraryGame.class));
    }

    @Test
    void execute_ShouldThrowLibraryGameAlreadyAddedException_WhenGameAlreadyAddedByUser() {
        // Arrange
        Game game = Game.builder().title("Test Game").build();
        AddLibraryGameRequest request = new AddLibraryGameRequest(
                "1",
                "Test Game",
                4.5,
                LibraryGameStatus.PLAYED,
                "user123"
        );
        when(gameRepository.findGame("Test Game"))
                .thenReturn(Optional.of(game));
        when(libraryGameRepository.findByTitleAndUsername("Test Game", "user123"))
                .thenReturn(LibraryGame.builder().build());

        // Act & Assert
        assertThrows(LibraryGameAlreadyAddedException.class, () -> addLibraryGameUseCase.execute(request));
        verify(libraryGameRepository, never()).save(any(LibraryGame.class));
    }

    @Test
    void execute_ShouldHandleDifferentStatuses() {
        Game game = Game.builder().title("Test Game").build();
        // Arrange
        AddLibraryGameRequest request = new AddLibraryGameRequest(
                "1",
                "Test Game",
                4.5,
                LibraryGameStatus.PLAYING,
                "user123"
        );
        when(gameRepository.findGame("Test Game"))
                .thenReturn(Optional.of(game));
        when(libraryGameRepository.findByTitleAndUsername("Test Game", "user123"))
                .thenReturn(null);

        // Act
        addLibraryGameUseCase.execute(request);

        // Assert
        verify(libraryGameRepository, times(1)).save(any(LibraryGame.class));
    }

    @Test
    void execute_ShouldHandleDifferentRatings() {
        // Arrange
        Game game = Game.builder().title("Test Game").build();
        AddLibraryGameRequest request1 = new AddLibraryGameRequest(
                "1",
                "Test Game",
                0.0,
                LibraryGameStatus.PLAYED,
                "user123"
        );
        AddLibraryGameRequest request2 = new AddLibraryGameRequest(
                "2",
                "Test Game",
                5.0,
                LibraryGameStatus.PLAYED,
                "user456"
        );
        when(gameRepository.findGame("Test Game"))
                .thenReturn(Optional.of(game));
        when(libraryGameRepository.findByTitleAndUsername(anyString(), anyString()))
                .thenReturn(null);

        // Act
        addLibraryGameUseCase.execute(request1);
        addLibraryGameUseCase.execute(request2);

        // Assert
        verify(libraryGameRepository, times(2)).save(any(LibraryGame.class));
    }
}
