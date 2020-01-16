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
    | timestamp              | customer      | merchant      | amount |
    | "24/12/2019 12:11:58" | "220866-1111" | "220866-3333" | 200    |
    | "30/11/2019 12:11:58" | "220866-1111" | "220866-4444" | 200    |
    | "01/12/2019 12:11:58" | "220866-2222" | "220866-3333" | 200    |
    | "31/12/2019 12:11:58" | "220866-2222" | "220866-4444" | 200    |
    When DTU Pay sends out the monthly reports for "December" 2019
#    Alternatively: When the current datetime is "01/01/2020 09:00:00"
#    Then DTU Pay will send out the monthly report for December 2020
    Then customer "220866-1111" will have 1 transaction in his report
    And customer "220866-2222" will have 2 transactions in his report


#    Scenario: No transactions this month
#    Given customer DTU Pay account "Bob Clark", ID "220866-2859", and 3 unused token
#    And merchant DTU Pay account "Alice Klaus", ID "220866-2858"
#    And merchant DTU Pay account "Chris White", ID "220866-2857"
