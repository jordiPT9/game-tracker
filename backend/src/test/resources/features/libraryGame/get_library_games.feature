Feature: Get library games
  As a basic user
  I want to be able to retrieve games of my library
  So i can see all of its information

  Background:
    Given the following roles exist:
      | USER |
    And a user with username "johnsmith" and role "USER" is logged in

  Scenario: User gets an existing game of their library successfully
    Given the following library-games exist:
      | id                                   | title             | rating | status | username  |
      | da1e846d-25e1-44e7-91f8-3cca9348d1b6 | Random game title | 4.5    | PLAYED | johnsmith |
    When the authenticated user sends a "GET" request to "/api/library-games/da1e846d-25e1-44e7-91f8-3cca9348d1b6"
    Then the server responds with a 200 status code
    And the response body should have the following JSON format "/response_schemas/get_library_game_schema.json"

  Scenario: User fails to get a non existing game of their library
    When the authenticated user sends a "GET" request to "/api/library-games/random_nonexistent_id"
    Then the server responds with a 404 status code

  Scenario: User fails to get a game of another users library
    Given the following users exist:
      | id          | username     | password        | email                  | role |
      | random_id_2 | marcgonzalez | marcgonzalez123 | marcgonzalez@email.com | USER |
    Given the following library-games exist:
      | id                                   | title             | rating | status | username     |
      | da1e846d-25e1-44e7-91f8-3cca9348d1b6 | Random game title | 4.5    | PLAYED | marcgonzalez |
    When the authenticated user sends a "GET" request to "/api/library-games/da1e846d-25e1-44e7-91f8-3cca9348d1b6"
    Then the server responds with a 403 status code

  Scenario: User gets all games of their library
    Given the following library-games exist:
      | id                                   | title              | rating | status    | username  |
      | da1e846d-25e1-44e7-91f8-3cca9348d1b6 | Random game title  | 4.5    | PLAYED    | johnsmith |
      | ef227608-ec18-40e7-b93f-714c8af40fc2 | Another game title | 0      | ABANDONED | johnsmith |
    When the authenticated user sends a "GET" request to "/api/library-games"
    Then the server responds with a 200 status code
    And the response body should have the following JSON format "/response_schemas/get_library_games_schema.json"
