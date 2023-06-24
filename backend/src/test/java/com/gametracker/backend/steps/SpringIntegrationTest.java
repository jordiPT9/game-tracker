package com.gametracker.backend.steps;

import com.gametracker.backend.game.domain.GameService;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SpringIntegrationTest {
    @MockBean
    private GameService gameService;
}
