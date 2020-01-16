Feature: Monthly reporting of transactions for a customer
  Scenario: Successful reporting
    Given the following customers:
    | id            | name         |
    | "220866-1111" | "Bob Cust"   |
    | "220866-2222" | "Alice Cust" |
    And the following merchants:
    | id            | name         |
    | "220866-3333" | "Bob Merc"   |
    | "220866-4444" | "Alice Merc" |
    And the following transactions:
    | timestamp             | customer    | merchant    | tokenId | amount |
    | 2019-12-24 12:11:58.1 | 220866-1111 | 220866-3333 | 1       | 200    |
    | 2019-11-30 12:11:58.1 | 220866-1111 | 220866-4444 | 2       | 200    |
    | 2019-12-01 12:11:58.1 | 220866-2222 | 220866-3333 | 3       | 200    |
    | 2019-12-31 12:11:58.1 | 220866-2222 | 220866-4444 | 4       | 200    |
    When DTU Pay sends out the monthly reports for "December" 2019
#    Alternatively: When the current datetime is "01/01/2020 09:00:00"
#    Then DTU Pay will send out the monthly report for December 2020
    Then customer "220866-1111" will have 1 transaction in his report
    And customer "220866-2222" will have 2 transactions in his report
    And customer "220866-2222" will have 2 transactions in his report

