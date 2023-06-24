package com.gametracker.backend.steps;

import com.gametracker.backend.libraryGame.domain.LibraryGameStatus;
import com.gametracker.backend.libraryGame.domain.LibraryGame;
import com.gametracker.backend.libraryGame.domain.LibraryGameRepository;
import com.gametracker.backend.game.domain.GameService;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

public class LibraryGameSteps {
    private final LibraryGameRepository libraryGameRepository;

    public LibraryGameSteps(LibraryGameRepository libraryGameRepository, GameService igdbService) {
        this.libraryGameRepository = libraryGameRepository;
    }

    @DataTableType
    public LibraryGame showTransformer(Map<String, String> entry) {
        return new LibraryGame(
                entry.get("id"),
                entry.get("title"),
                Double.parseDouble(entry.get("rating")),
                LibraryGameStatus.valueOf(entry.get("status")),
                entry.get("username")
        );
    }

    @Given("the following library-games exist:")
    public void theFollowingLibraryGameExists(List<LibraryGame> libraryGames) {
        libraryGames.forEach(libraryGameRepository::save);
    }

    @And("the following library-games should be in the database:")
    public void theFollowingLibraryGamesShouldBeInTheDatabase(List<LibraryGame> libraryGames) {
        libraryGames.forEach(expectedLibraryGame -> {
            LibraryGame actualLibraryGame = libraryGameRepository.findById(expectedLibraryGame.getId());
            assertEquals(expectedLibraryGame, actualLibraryGame);
        });
    }

    @And("the following library-games should not be in the database:")
    public void theFollowingLibraryGamesShouldNotBeInTheDatabase(List<LibraryGame> libraryGames) {
        libraryGames.forEach(expectedLibraryGame -> {
            LibraryGame actualLibraryGame = libraryGameRepository.findById(expectedLibraryGame.getId());
            assertNull(actualLibraryGame);
        });
    }
}
