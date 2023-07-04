package com.gametracker.backend.steps;

import com.gametracker.backend.libraryGame.domain.LibraryGameRepository;
import com.gametracker.backend.role.domain.RoleName;
import com.gametracker.backend.role.domain.RoleRepository;
import com.gametracker.backend.security.domain.UnauthorizedException;
import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestApiSteps {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final LibraryGameRepository libraryGameRepository;
    private final RestTemplate restTemplate;

    private String currentJwt;
    private ResponseEntity<String> latestResponse;

    private final String BASE_URL = "http://localhost:5000";

    public RestApiSteps(RoleRepository roleRepository,
                        UserRepository userRepository,
                        LibraryGameRepository libraryGameRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.libraryGameRepository = libraryGameRepository;

        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        restTemplate = new RestTemplate(factory);
    }

    @Before
    public void beforeEachScenario() {
        libraryGameRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Given("an authenticated user with username {string} and role {string}")
    public void anAuthenticatedUserWithUsernameAndRole(String username, String role) {
        RoleName roleName = RoleName.valueOf(role);
        User user = new User("random id", username, "random password", "johnsmith@mail.com", roleName);
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
            latestResponse = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
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

    @And("the response body should have the following JSON format {string}:")
    public void theResponseBodyShouldHaveTheFollowingJSONFormat(String schemaPath) {
        assertDoesNotThrow(() -> {
            String schemaString = new String(getClass().getResourceAsStream(schemaPath).readAllBytes());
            JSONObject schemaJson = new JSONObject(schemaString);
            String schemaType = schemaJson.getString("type");

            if (schemaType.equals("object")) {
                validateResponseAsJsonObject(schemaJson);
            } else if (schemaType.equals("array")) {
                validateResponseAsJsonArray(schemaJson);
            }
        });
    }

    @And("the response body should contain the error message {string}")
    public void theMessageBodyShouldBe(String expectedMessage) {
        assertEquals(expectedMessage, latestResponse.getBody());
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentJwt);
        return headers;
    }

    private void validateResponseAsJsonArray(JSONObject schemaJson) {
        JSONArray responseBodyJson = new JSONArray(latestResponse.getBody());
        Schema schema = SchemaLoader.load(schemaJson);
        schema.validate(responseBodyJson);
    }

    private void validateResponseAsJsonObject(JSONObject schemaJson) {
        JSONObject responseBodyJson = new JSONObject(latestResponse.getBody());
        Schema schema = SchemaLoader.load(schemaJson);
        schema.validate(responseBodyJson);
    }
}
