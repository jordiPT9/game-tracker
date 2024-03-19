package com.gametracker.backend.acceptance.steps;

import com.gametracker.backend.game.domain.GameRepository;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SpringIntegrationTest {
  static MySQLContainer mysql =
      new MySQLContainer(DockerImageName.parse("mysql:5.7.34")).withDatabaseName("gametracker");

  @DynamicPropertySource
  static void startMySQLContainer(DynamicPropertyRegistry registry) {
    mysql.start();
    registry.add("spring.datasource.url", mysql::getJdbcUrl);
    registry.add("spring.datasource.username", mysql::getUsername);
    registry.add("spring.datasource.password", mysql::getPassword);
    registry.add("spring.datasource.driverClassName", mysql::getDriverClassName);
  }

  @MockBean private GameRepository gameRepository;
}
