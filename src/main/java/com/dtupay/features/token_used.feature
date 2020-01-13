Feature: Token is already used
  Scenario: Unsuccessful transfer
    Given bank account "Bob", "Clark", "220866-2859" with starting balance 1000
    And bank account "Alice", "Klaus", "220866-2858" with starting balance 0
    And customer DTU Pay account "Bob Clark", ID "220866-2859", and 1 used token
    And merchant DTU Pay account "Alice Klaus", ID "220866-2858", and 0 used tokens
    When the merchant scans the customer's used token
    Then the system detects the token has already been used
