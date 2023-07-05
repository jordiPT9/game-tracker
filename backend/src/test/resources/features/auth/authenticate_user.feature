Feature: User authentication
  As a potential user
  I want to be able to authenticate
  So i can use the services

  Background:
    Given the following roles exist:
      | USER |
    And the following users exist:
      | id        | username | password    | email              | role |
      | random_id | johnsnow | johnsnow123 | johnsnow@email.com | USER |

  Scenario: User authenticates successfully
    When the unauthenticated user sends a "POST" request to "/api/authenticate" with the following JSON body:
      """
      {
        "username": "johnsnow",
        "password": "johnsnow123"
      }
      """
    Then the server responds with a 200 status code
    And the response body should have the following JSON format "/schemas/authenticate_user_schema.json":

  Scenario: User fails to authenticate when user is not found
    When the unauthenticated user sends a "POST" request to "/api/authenticate" with the following JSON body:
      """
      {
        "username": "random_nonexistent_username",
        "password": "johnsnow123"
      }
      """
    Then the server responds with a 401 status code

  Scenario: User fails to authenticate when password is wrong
    When the unauthenticated user sends a "POST" request to "/api/authenticate" with the following JSON body:
      """
      {
        "username": "johnsnow",
        "password": "random_invalid_password"
      }
      """
    Then the server responds with a 401 status code