Feature: Register user
  As a potential user
  I want to be able to register
  So i can log in to the app

  Background:
    Given the following roles exist:
      | USER |

  Scenario: Potential user registers successfully
    When the unauthenticated user sends a "POST" request to "/api/users" with the following JSON body:
      """
      {
        "id": "da1e846d-25e1-44e7-91f8-3cca9348d1b6",
        "username": "johnsmith",
        "password": "johnsmith1234",
        "email": "johnsmith@email.com",
        "role": "USER"
      }
      """
    Then the server responds with a 201 status code
    And users with the following usernames should be in the database:
      | johnsmith |