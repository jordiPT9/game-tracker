Feature: Get average users rating from a game
  As a user
  I want to be able to know the average rating of a game
  So I can make an informed decision about whether to play or purchase the game

  Background:
    Given the following roles exist:
      | ROLE_USER |
    And a user with username "johnsmith" and role "ROLE_USER" is logged in

  Scenario: User gets average rating from a game successfully
    Given the following users exist:
      | id          | username     | password        | email                  | role      |
      | random_id_2 | marcgonzalez | marcgonzalez123 | marcgonzalez@email.com | ROLE_USER |
      | random_id_3 | albertgarcia | albertgarcia123 | albertgarcia@email.com | ROLE_USER |
      | random_id_4 | ramonjimenez | ramonjimenez123 | ramonjimenez@email.com | ROLE_USER |
    Given the following library-games exist:
      | id                                   | title  | rating | status | username     |
      | aa1e846d-25e1-44e7-91f8-3cca9348d1b6 | mario  | 7.5    | PLAYED | albertgarcia |
      | ba1e846d-25e1-44e7-91f8-3cca9348d1b6 | mario  | 8.5    | PLAYED | marcgonzalez |
      | ca1e846d-25e1-44e7-91f8-3cca9348d1b6 | mario  | 9.5    | PLAYED | ramonjimenez |
      | da1e846d-25e1-44e7-91f8-3cca9348d1b6 | tetris | 3.5    | PLAYED | marcgonzalez |
    When the authenticated user sends a "GET" request to "/api/reports/average-game-rating/mario"
    Then the server responds with a 200 status code
    And the response body should have the following JSON format "/response_schemas/get_average_game_rating_schema.json"
