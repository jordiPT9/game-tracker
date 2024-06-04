Feature: Delete library game
  As a basic user
  I want to remove a game of my library
  So I can manage my library and keep it up to date with my preferences

  Background:
    Given the following roles exist:
      | ROLE_USER |
    And a user with username "johnsmith" and role "ROLE_USER" is logged in

  Scenario: User removes a game of their library successfully
    Given the following library-games exist:
      | id                                   | title             | rating | status | username  |
      | da1e846d-25e1-44e7-91f8-3cca9348d1b6 | Random game title | 4.5    | PLAYED | johnsmith |
    When the authenticated user sends a "DELETE" request to "/api/library-games/da1e846d-25e1-44e7-91f8-3cca9348d1b6"
    Then the server responds with a 204 status code
    And library-games with the following ids should not be in the database:
      | da1e846d-25e1-44e7-91f8-3cca9348d1b6 |

  Scenario: User fails to remove a game of their library when it doesn't exist
    When the authenticated user sends a "DELETE" request to "/api/library-games/random_id"
    Then the server responds with a 404 status code
    And the response body should contain the message "Library game not found with id 'random_id'"
