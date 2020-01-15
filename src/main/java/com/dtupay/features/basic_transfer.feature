Feature: Basic DTU Pay money transfer
  Scenario: Successful transfer
    Given customer DTU Pay account "Bob Clark", ID "220866-2859", and 1 unused token
    And merchant DTU Pay account "Alice Klaus", ID "220866-2858"
    And bank account "Bob Clark", "220866-2859" with start balance 1000
    And bank account "Alice Klaus", "220866-2858" with start balance 0
    When the merchant scans the customer's token
    Then the amount 300 is transferred to the merchant
    And the balance of the customer is 700
    And the balance of the merchant is 300

  Scenario: Unsuccessful transfer
    Given The bank account "Bob Clark", with ID "220866-2859" with starting balance 1000
    And The bank account "Alice Klaus", with ID "220866-2858" with starting balance 0
    And The customer DTU Pay account "Bob Clark", with ID "220866-2859", and 1 invalid token
    And The merchant DTU Pay account "Alice Klaus", with ID "220866-2858"
    When The merchant scans the customer's invalid token
    Then Token is not found in the DTUPay system
