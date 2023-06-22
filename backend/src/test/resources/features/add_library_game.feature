Feature: Adding a game to the users library

  Background:
    Given an authenticated user with username "johnsmith" and role "USER"

  Scenario: User adds a game to their library successfully
    When the authenticated user sends a "POST" request to "/api/library-games" with the following JSON body:
      """
      {
        "id": "da1e846d-25e1-44e7-91f8-3cca9348d1b6",
        "title": "Test Game",
        "rating": 4.5,
        "status": "PLAYING"
      }
      """
    Then the server responds with a 201 status code
    And the following library games should be in the database:
      | id                                   | title     | rating | status  | username  |
      | da1e846d-25e1-44e7-91f8-3cca9348d1b6 | Test Game | 4.5    | PLAYING | johnsmith |

  Scenario: User fails to add an already existing game in their library
    Given the following library games exist:
      | id                                   | title     | rating | status | username  |
      | da1e846d-25e1-44e7-91f8-3cca9348d1b6 | Test Game | 4.5    | PLAYED | johnsmith |
    When the authenticated user sends a "POST" request to "/api/library-games" with the following JSON body:
      """
      {
        "id": "da1e846d-25e1-44e7-91f8-3cca9348d1b6",
        "title": "Test Game",
        "rating": 4.5,
        "status": "PLAYING"
      }
      """
    Then the server responds with a 409 status code

  Scenario: User fails to add a game that doesnt exist
    Given a game that doesn't exist
    When the authenticated user sends a "POST" request to "/api/library-games" with the following JSON body:
      """
      {
        "id": "ba1e846d-25e1-44e7-91f8-3cca9348d1b6",
        "title": "This game doesnt exist",
        "rating": 7.5,
        "status": "PLAYED"
      }
      """
    Then the server responds with a 409 status code