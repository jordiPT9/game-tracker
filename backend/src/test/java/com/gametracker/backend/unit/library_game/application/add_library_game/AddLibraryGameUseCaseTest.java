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

        addLibraryGameUseCase.execute(request);

        verify(libraryGameRepository, times(1)).save(any(LibraryGame.class));
    }

    @Test
    void execute_ShouldThrowGameDoesNotExistException_WhenGameDoesNotExist() {
        AddLibraryGameRequest request = new AddLibraryGameRequest(
                "1",
                "Nonexistent Game",
                4.5,
                LibraryGameStatus.PLAYED,
                "user123"
        );
        when(gameRepository.findGame("Nonexistent Game"))
                .thenReturn(Optional.empty());

        assertThrows(GameDoesNotExistException.class, () -> addLibraryGameUseCase.execute(request));
        verify(libraryGameRepository, never()).save(any(LibraryGame.class));
    }

    @Test
    void execute_ShouldThrowLibraryGameAlreadyAddedException_WhenGameAlreadyAddedByUser() {
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

        assertThrows(LibraryGameAlreadyAddedException.class, () -> addLibraryGameUseCase.execute(request));
        verify(libraryGameRepository, never()).save(any(LibraryGame.class));
    }
}
