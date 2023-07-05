package com.gametracker.backend.steps;

import com.gametracker.backend.shared.infrastructure.DatabaseTruncator;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestApiSteps {
    private final UserRepository userRepository;
    private final DatabaseTruncator databaseTruncator;
    private final RestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:5000";
    private String currentJwt;
    private ResponseEntity<String> latestResponse;

    public RestApiSteps(UserRepository userRepository, DatabaseTruncator databaseTruncator) {
        this.userRepository = userRepository;
        this.databaseTruncator = databaseTruncator;
        restTemplate = new RestTemplate();
    }

    @Before
    public void beforeEachScenario() {
        databaseTruncator.truncateAllTables();
    }

    @Given("the following user successfully logs in:")
    public void theFollowingUserLogsIn(User user) {
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

    @And("the response body should contain the message {string}")
    public void theResponseBodyShouldContainTheMessage(String expectedMessage) {
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
