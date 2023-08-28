package com.gametracker.backend.steps;

import com.gametracker.backend.game.domain.GameRepository;
import com.gametracker.backend.library_game.domain.LibraryGame;
import com.gametracker.backend.library_game.domain.LibraryGameRepository;
import com.gametracker.backend.library_game.domain.LibraryGameStatus;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LibraryGameSteps {
    private final LibraryGameRepository libraryGameRepository;

    public LibraryGameSteps(LibraryGameRepository libraryGameRepository, GameRepository igdbService) {
        this.libraryGameRepository = libraryGameRepository;
    }

    @DataTableType
    public LibraryGame libraryGameTransformer(Map<String, String> entry) {
        return LibraryGame.builder()
                .id(entry.get("id"))
                .title(entry.get("title"))
                .rating(Double.parseDouble(entry.get("rating")))
                .status(LibraryGameStatus.valueOf(entry.get("status")))
                .username(entry.get("username"))
                .build();
    }

    @Given("the following library-games exist:")
    public void theFollowingLibraryGameExists(List<LibraryGame> libraryGames) {
        libraryGames.forEach(libraryGameRepository::save);
    }

    @And("library-games with the following ids should be in the database:")
    public void theFollowingLibraryGamesShouldBeInTheDatabase(List<String> libraryGameIds) {
        libraryGameIds.forEach(id -> {
            LibraryGame libraryGame = libraryGameRepository.findById(id);
            assertNotNull(libraryGame);
        });
    }

    @And("library-games with the following ids should not be in the database:")
    public void theFollowingLibraryGamesShouldNotBeInTheDatabase(List<String> libraryGameIds) {
        libraryGameIds.forEach(id -> {
            LibraryGame libraryGame = libraryGameRepository.findById(id);
            assertNull(libraryGame);
        });
    }
}
