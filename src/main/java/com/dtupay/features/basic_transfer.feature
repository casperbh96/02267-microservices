Feature: Basic DTU Pay money transfer
  Scenario: Successful transfer
    Given bank account "Bob", "Clark", "220866-2859" with start balance 1000
    And bank account "Alice", "Klaus", "220866-2858" with start balance 0
    And customer DTU Pay account "Bob Clark", ID "220866-2859", and 1 unused token
    And merchant DTU Pay account "Alice Klaus", ID "220866-2858", and 0 tokens
    When the merchant scans the customer's token
    Then the amount 300 is transferred to the merchant
    And the balance of the customer is 700
    And the balance of the merchant is 300