package com.gametracker.backend.steps;

import com.gametracker.backend.role.domain.RoleName;
import com.gametracker.backend.shared.infrastructure.persistence.DatabaseTruncator;
import com.gametracker.backend.user.domain.User;
import com.gametracker.backend.user.domain.UserRepository;
import com.github.javafaker.Faker;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.*;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestApiSteps {
    private final String BASE_URL = "http://localhost:5000";

    private final UserRepository userRepository;
    private final DatabaseTruncator databaseTruncator;
    private final OkHttpClient okHttpClient;

    private String currentJwt;
    private Response latestResponse;

    public RestApiSteps(UserRepository userRepository, DatabaseTruncator databaseTruncator) {
        this.userRepository = userRepository;
        this.databaseTruncator = databaseTruncator;
        this.okHttpClient = new OkHttpClient().newBuilder().build();
    }

    @Before
    public void beforeEachScenario() {
        databaseTruncator.truncateAllTables();
    }

    @Given("a user with username {string} and role {string} is logged in")
    public void theFollowingUserLogsIn(String username, String role) throws IOException {
        Faker faker = new Faker();
        User user = User.builder()
                .id(faker.internet().uuid())
                .username(username)
                .password(faker.internet().password())
                .email(faker.internet().emailAddress())
                .role(RoleName.valueOf(role))
                .build();
        userRepository.save(user);

        currentJwt = loginAndGetAccessToken(user.getUsername(), user.getPassword());
    }

    private String loginAndGetAccessToken(String username, String password) throws IOException {
        var requestBody = new JSONObject()
                .put("username", username)
                .put("password", password);

        var mediaType = okhttp3.MediaType.parse("application/json");
        var body = RequestBody.create(requestBody.toString(), mediaType);
        var request = new Request.Builder()
                .url(BASE_URL + "/api/auth/login")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        var response = okHttpClient.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("accessToken");
    }

    @When("the unauthenticated user sends a {string} request to {string} with the following JSON body:")
    public void unauthenticatedUserSendsRequestWithBody(String method, String uri, String jsonBody) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(jsonBody, mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + uri)
                .method(method, body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        latestResponse = okHttpClient.newCall(request).execute();
    }

    @When("the authenticated user sends a {string} request to {string}")
    public void authenticatedUserSendsRequest(String method, String uri) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(BASE_URL + uri)
                .addHeader("Authorization", "Bearer " + currentJwt)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json");

        if (!method.equals("GET")) {
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create("", mediaType);
            requestBuilder.method(method, body);
        }

        Request request = requestBuilder.build();
        latestResponse = okHttpClient.newCall(request).execute();
    }

    @When("the authenticated user sends a {string} request to {string} with the following JSON body:")
    public void authenticatedUserSendsRequestWithJsonBody(String method, String uri, String jsonBody) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(jsonBody, mediaType);
        Request request = new Request.Builder()
                .url(BASE_URL + uri)
                .method(method, body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + currentJwt)
                .build();
        latestResponse = okHttpClient.newCall(request).execute();
    }

    @Then("the server responds with a {int} status code")
    public void serverRespondsWithStatusCode(int expectedStatusCode) {
        assertEquals(expectedStatusCode, latestResponse.code());
    }

    @And("the response body should have the following JSON format {string}")
    public void responseBodyShouldHaveTheFollowingJSONFormat(String schemaPath) throws IOException {
        String body = latestResponse.body().string();
        System.out.printf("Response body: %s", body);

        String schemaString = new String(getClass().getResourceAsStream(schemaPath).readAllBytes());
        JSONObject schemaJson = new JSONObject(schemaString);
        String schemaType = schemaJson.getString("type");

        assertDoesNotThrow(() -> {
            try {
                if (schemaType.equals("object")) {
                    validateResponseAsJsonObject(schemaJson, body);
                } else if (schemaType.equals("array")) {
                    validateResponseAsJsonArray(schemaJson, body);
                }
            } catch (ValidationException validationException) {
                printValidationErrors(validationException);
                throw validationException;
            }
        });
    }

    private void validateResponseAsJsonArray(JSONObject schemaJson, String body) {
        JSONArray responseBodyJson = new JSONArray(body);
        Schema schema = SchemaLoader.load(schemaJson);
        schema.validate(responseBodyJson);
    }

    private void validateResponseAsJsonObject(JSONObject schemaJson, String body) {
        JSONObject responseBodyJson = new JSONObject(body);
        Schema schema = SchemaLoader.load(schemaJson);
        schema.validate(responseBodyJson);
    }

    private void printValidationErrors(ValidationException e) {
        List<String> errorMessages = e.getAllMessages();
        System.err.println("Schema validation failed with the following errors:");
        System.err.println(e.getMessage());
        for (String errorMessage : errorMessages) {
            System.err.println("- " + errorMessage);
        }
    }

    @And("the response body should contain the message {string}")
    public void responseShouldContainTheMessage(String expectedMessage) throws IOException {
        assertEquals(expectedMessage, latestResponse.body().string());
    }
}
