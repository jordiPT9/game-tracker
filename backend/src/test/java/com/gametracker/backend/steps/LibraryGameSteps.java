package com.gametracker.backend.steps;

import com.gametracker.backend.libraryGame.domain.GameStatus;
import com.gametracker.backend.libraryGame.domain.LibraryGame;
import com.gametracker.backend.libraryGame.domain.LibraryGameRepository;
import com.gametracker.backend.shared.infrastructure.IgdbService;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibraryGameSteps {
    private final LibraryGameRepository libraryGameRepository;
    private final IgdbService igdbService;

    public LibraryGameSteps(LibraryGameRepository libraryGameRepository, IgdbService igdbService) {
        this.libraryGameRepository = libraryGameRepository;
        this.igdbService = igdbService;
    }

    @DataTableType
    public LibraryGame showTransformer(Map<String, String> entry) {
        return new LibraryGame(
                entry.get("id"),
                entry.get("title"),
                Double.parseDouble(entry.get("rating")),
                GameStatus.valueOf(entry.get("status")),
                entry.get("username")
        );
    }

    @Given("the following library games exist:")
    public void theFollowingLibraryGameExists(List<LibraryGame> libraryGames) {
        libraryGames.forEach(libraryGameRepository::save);
    }

    @And("the following library games should be in the database:")
    public void theFollowingLibraryGamesShouldBeInTheDatabase(List<LibraryGame> libraryGames) {
        libraryGames.forEach(expectedLibraryGame -> {
            LibraryGame actualLibraryGame = libraryGameRepository.findById(expectedLibraryGame.getId());
            assertEquals(expectedLibraryGame, actualLibraryGame);
        });
    }

    @Given("a game that doesn't exist")
    public void aGameThatDoesnTExist() {

    }
}
