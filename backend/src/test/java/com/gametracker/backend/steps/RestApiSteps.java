package com.gametracker.backend.steps;

import com.gametracker.backend.game.domain.Game;
import com.gametracker.backend.game.domain.GameService;
import com.gametracker.backend.libraryGame.domain.LibraryGameRepository;
import com.gametracker.backend.role.domain.RoleName;
import com.gametracker.backend.role.domain.RoleRepository;
import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class RestApiSteps {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final LibraryGameRepository libraryGameRepository;
    private final GameService gameService;

    private final String BASE_URL = "http://localhost:5000";
    private final RestTemplate restTemplate = new RestTemplate();

    private String currentJwt;
    private ResponseEntity<String> latestResponse;

    public RestApiSteps(RoleRepository roleRepository,
                        UserRepository userRepository,
                        LibraryGameRepository libraryGameRepository,
                        GameService gameService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.libraryGameRepository = libraryGameRepository;
        this.gameService = gameService;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentJwt);
        return headers;
    }

    @Before
    public void beforeEachScenario() {
        libraryGameRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Given("the game exists")
    public void theGameExists() {
        when(gameService.searchGame(anyString())).thenReturn(new Game("random_title", 5, "random_release_date"));
    }

    @Given("the game does not exist")
    public void theGameDoesNotExist() {
        when(gameService.searchGame(anyString())).thenReturn(null);
    }

    @Given("an authenticated user with username {string} and role {string}")
    public void anAuthenticatedUserWithUsernameAndRole(String username, String role) {
        RoleName roleName = RoleName.valueOf(role);
        User user = new User(
                "random id",
                username,
                "random password",
                "johnsmith@mail.com",
                roleName
        );
        roleRepository.save(roleName);
        userRepository.save(user);

        JSONObject requestBody = new JSONObject()
                .put("username", user.getUsername())
                .put("password", user.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        latestResponse = restTemplate.exchange(
                BASE_URL + "/api/authenticate",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        JSONObject responseBody = new JSONObject(latestResponse.getBody());
        currentJwt = responseBody.getString("jwt");
    }

    @When("the unauthenticated user sends a {string} request to {string} with the following JSON body:")
    public void unauthenticatedUserSendsRequestWithBody(String method, String uri, String jsonBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        try {
            latestResponse = restTemplate.exchange(
                    BASE_URL + uri,
                    HttpMethod.valueOf(method),
                    requestEntity,
                    String.class
            );
        } catch (HttpClientErrorException e) {
            latestResponse = new ResponseEntity<>(e.getStatusCode());
        }
    }

    @When("the authenticated user sends a {string} request to {string}:")
    public void theAuthenticatedUserSendsARequestTo(String httpMethod, String uri) {
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            latestResponse = restTemplate.exchange(
                    BASE_URL + uri,
                    HttpMethod.valueOf(httpMethod),
                    requestEntity,
                    String.class
            );
        } catch (HttpClientErrorException e) {
            latestResponse = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    @When("the authenticated user sends a {string} request to {string} with the following JSON body:")
    public void authenticatedUserSendsRequestWithBody(String httpMethod, String uri, String jsonBody) {
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        try {
            latestResponse = restTemplate.exchange(
                    BASE_URL + uri,
                    HttpMethod.valueOf(httpMethod),
                    requestEntity,
                    String.class
            );
        } catch (HttpClientErrorException e) {
            latestResponse = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    @Then("the server responds with a {int} status code")
    public void serverRespondsWithACreatedStatusCode(int expectedStatusCode) {
        HttpStatusCode statusCode = latestResponse.getStatusCode();
        assertEquals(expectedStatusCode, statusCode.value());
    }

    @And("the response body should have the following JSON format:")
    public void theResponseBodyShouldHaveTheFollowingJSONFormat(String jsonSchema) {
        JSONObject jsonObject = new JSONObject(latestResponse.getBody());
        JSONObject schemaObject = new JSONObject(jsonSchema);
        Schema schema = SchemaLoader.load(schemaObject);

        assertDoesNotThrow(() -> schema.validate(jsonObject));
    }
}
