Feature: Update user email
  As a user
  I want to be able to change my email address
  So I can ensure my account information is up-to-date and receive notifications at my preferred email address

  Background:
    Given the following roles exist:
      | ROLE_USER |
    And a user with username "johnsmith" and role "ROLE_USER" is logged in

  Scenario: User updates email successfully
    When the authenticated user sends a "PUT" request to "/api/users/johnsmith?email=test@email.com"
    Then the server responds with a 200 status code

  Scenario: User fails to update email when its invalid
    When the authenticated user sends a "PUT" request to "/api/users/johnsmith?email=thisisaninvalidemailaddress"
    Then the server responds with a 400 status code
    And the response body should contain the message "Email is invalid"