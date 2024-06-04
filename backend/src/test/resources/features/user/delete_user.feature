Feature: Delete user
  As an ADMIN user
  I want to be able to delete a user account
  So I can manage user accounts effectively and maintain the security and integrity of the system

  Background:
    Given the following roles exist:
      | ROLE_USER  |
      | ROLE_ADMIN |

  Scenario: Admin user deletes a basic user successfully
    Given a user with username "johnsmith" and role "ROLE_ADMIN" is logged in
    And the following users exist:
      | id           | username     | password        | email                  | role |
      | user_test_id | marcgonzalez | marcgonzalez123 | marcgonzalez@email.com | ROLE_USER |
    When the authenticated user sends a "DELETE" request to "/api/admin/users/user_test_id"
    Then the server responds with a 204 status code
    And users with the following usernames should not be in the database:
      | marcgonzalez |

  Scenario: Non-Admin user deletes a basic user successfully
    Given a user with username "johnsmith" and role "ROLE_USER" is logged in
    And the following users exist:
      | id           | username     | password        | email                  | role |
      | user_test_id | marcgonzalez | marcgonzalez123 | marcgonzalez@email.com | ROLE_USER |
    When the authenticated user sends a "DELETE" request to "/api/admin/users/user_test_id"
    Then the server responds with a 401 status code
