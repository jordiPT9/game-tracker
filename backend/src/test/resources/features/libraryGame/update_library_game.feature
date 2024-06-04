Feature: Update library game
  As a basic user
  I want to update a game of my library
  So that I can keep my game collection up to date with the latest versions

  Background:
    Given the following roles exist:
      | ROLE_USER |
    And a user with username "johnsmith" and role "ROLE_USER" is logged in


  Scenario: User updates an existing game of their library successfully
    Given the following library-games exist:
      | id                                   | title             | rating | status | username     |
      | da1e846d-25e1-44e7-91f8-3cca9348d1b6 | Random game title | 4.5    | PLAYED | johnsmith |
    When the authenticated user sends a "PUT" request to "/api/library-games/da1e846d-25e1-44e7-91f8-3cca9348d1b6" with the following JSON body:
      """
      {
        "rating": 5.9,
        "status": "PLAYED"
      }
      """
    Then the server responds with a 200 status code

  Scenario: User fails to update a non existent library game
    When the authenticated user sends a "PUT" request to "/api/library-games/da1e846d-25e1-44e7-91f8-3cca9348d1b6" with the following JSON body:
      """
      {
        "rating": 4.9,
        "status": "PLAYED"
      }
      """
    Then the server responds with a 404 status code
    And the response body should contain the message "Library game not found with id 'da1e846d-25e1-44e7-91f8-3cca9348d1b6'"
