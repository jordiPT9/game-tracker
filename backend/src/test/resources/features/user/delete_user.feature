Feature: Delete user
  As an ADMIN user
  I want to be able to delete a user account
  To be able to manage and maintain the users from the system

  Background:
    Given the following roles exist:
      | USER  |
      | ADMIN |
    And a user with username "johnsmith" and role "ADMIN" is logged in

  Scenario: Admin user deletes a basic user successfully
    Given the following users exist:
      | id           | username     | password        | email                  | role |
      | user_test_id | marcgonzalez | marcgonzalez123 | marcgonzalez@email.com | USER |
    When the authenticated user sends a "DELETE" request to "/api/admin/users/user_test_id"
    Then the server responds with a 204 status code
    And users with the following usernames should not be in the database:
      | marcgonzalez |