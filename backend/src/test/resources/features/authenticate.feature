Feature: User authentication

  Background:
    Given the following users exist:
      | uuid      | username | password    | email              | role |
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
    And the response body should have the following JSON format:
    """
    {
      "type": "object",
      "properties": {
        "jwt": {
          "type": "string"
        }
      },
      "required": ["jwt"]
    }
    """
