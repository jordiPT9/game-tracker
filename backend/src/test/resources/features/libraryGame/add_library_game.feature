Feature: Add library game
  As a basic user
  I want to add a game to my library
  So i can keep track of its status and personal rating

  Background:
    Given the following roles exist:
      | ROLE_USER |
    And a user with username "johnsmith" and role "ROLE_USER" is logged in


  Scenario: User adds an existing game to their library successfully
    Given the game exists
    When the authenticated user sends a "POST" request to "/api/library-games" with the following JSON body:
      """
      {
        "id": "da1e846d-25e1-44e7-91f8-3cca9348d1b6",
        "title": "Random game title",
        "rating": 4.5,
        "status": "PLAYING"
      }
      """
    Then the server responds with a 201 status code
    And library-games with the following ids should be in the database:
      | da1e846d-25e1-44e7-91f8-3cca9348d1b6 |

  Scenario: User fails to add an already existing game in their library
    Given the game exists
    And the following library-games exist:
      | id                                   | title             | rating | status | username  |
      | da1e846d-25e1-44e7-91f8-3cca9348d1b6 | Random game title | 4.5    | PLAYED | johnsmith |
    When the authenticated user sends a "POST" request to "/api/library-games" with the following JSON body:
      """
      {
        "id": "da1e846d-25e1-44e7-91f8-3cca9348d1b6",
        "title": "Random game title",
        "rating": 4.5,
        "status": "PLAYING"
      }
      """
    Then the server responds with a 409 status code
    And the response body should contain the message "Library game with title 'Random game title' is already added"

  Scenario: User fails to add a game that doesnt exist
    Given the game does not exist
    When the authenticated user sends a "POST" request to "/api/library-games" with the following JSON body:
      """
      {
        "id": "ba1e846d-25e1-44e7-91f8-3cca9348d1b6",
        "title": "Random game title",
        "rating": 7.5,
        "status": "PLAYED"
      }
      """
    Then the server responds with a 404 status code
    And the response body should contain the message "Game with title 'Random game title' doesn't exist"