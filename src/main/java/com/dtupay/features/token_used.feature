Feature: Token is already used
  Scenario: Unsuccessful transfer
    Given bank account "Bob Clark", with ID "220866-2859" with starting balance 1000
    And bank account "Alice Klaus", with ID "220866-2858" with starting balance 0
    And customer DTU Pay account "Bob Clark", with ID "220866-2859", and 1 used token
    And merchant DTU Pay account "Alice Klaus", with ID "220866-2858"
    When the merchant scans the customer's used token
    Then the system detects the token has already been used
