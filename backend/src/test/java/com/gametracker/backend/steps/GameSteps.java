package com.gametracker.backend.steps;

import com.gametracker.backend.game.domain.Game;
import com.gametracker.backend.game.domain.GameRepository;
import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class GameSteps {
  private final GameRepository gameRepository;

  public GameSteps(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  @Given("the game exists")
  public void theGameExists() {
    Faker faker = new Faker();
    Game randomGame =
        Game.builder()
            .title(faker.book().title())
            .follows(faker.number().numberBetween(0, 100))
            .releaseDate(faker.date().birthday().toString())
            .build();

    when(gameRepository.findGame(anyString())).thenReturn(Optional.of(randomGame));
  }

  @Given("the game does not exist")
  public void theGameDoesNotExist() {
    when(gameRepository.findGame(anyString())).thenReturn(Optional.empty());
  }
}
