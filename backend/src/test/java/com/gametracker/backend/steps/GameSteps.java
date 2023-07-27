package com.gametracker.backend.steps;

import com.gametracker.backend.game.domain.Game;
import com.gametracker.backend.game.domain.GameService;
import io.cucumber.java.en.Given;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class GameSteps {
    private final GameService gameService;

    public GameSteps(GameService gameService) {
        this.gameService = gameService;
    }

    @Given("the game exists")
    public void theGameExists() {
        when(gameService.getGame(anyString())).thenReturn(new Game("random_title", 5, "random_release_date"));
    }

    @Given("the game does not exist")
    public void theGameDoesNotExist() {
        when(gameService.getGame(anyString())).thenReturn(null);
    }
}
