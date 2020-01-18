Feature: Monthly reporting of transactions for a customer
  Scenario: Successful reporting
    Given the following customers:
    | id | name       |
    | 1  | Bob Cust   |
    | 2  | Alice Cust |
    And the following merchants:
    | id | name       |
    | 3  | Bob Merc   |
    | 4  | Alice Merc |
    And the following transactions:
    | timestamp             | fromId | toId | tokenId | amount | isRefund |
    | 2019-12-24 12:11:58 | 1      | 3    | 1       | 200    | false    |
    | 2019-11-30 12:11:58 | 1      | 3    | 2       | 200    | false    |
    | 2019-12-01 12:11:58 | 2      | 3    | 3       | 200    | false    |
    | 2019-12-31 12:11:58 | 2      | 4    | 4       | 200    | false    |
    And the current date and time is 2020-01-01 00:01:00
    When DTU Pay sends out the monthly reports for "December" 2019
#    Alternatively: When the current datetime is "01/01/2020 09:00:00"
#    Then DTU Pay will send out the monthly report for December 2020
    Then customer 1 will have 1 transaction in his report
    And customer 2 will have 2 transactions in his report

