Feature: Monthly reporting of transactions
  Scenario: Successful reporting for customer
    Given the following transactions:
    | timestamp             | from   | to   | tokenId | amount | isRefund |
    | 2019-12-24 12:11:58.0 | 1      | 3    | 1       | 100    | false    |
    | 2019-11-30 12:12:58.0 | 1      | 3    | 2       | 200    | false    |
    | 2019-12-01 12:13:58.0 | 2      | 3    | 3       | 300    | false    |
    | 2019-12-31 12:14:58.0 | 2      | 4    | 4       | 400    | false    |
    | 2019-12-31 12:15:58.0 | 4      | 2    | 5       | 500    | true     |
    When transaction manager generates monthly customer reports for 12 2019
    Then the following customer transactions are in the reports:
    | customerId  | timestamp             | tokenId | amount | isRefund | to |
    | 1           | 2019-12-24 12:11:58.0 | 1       | 100    | false    | 3  |
    | 2           | 2019-12-01 12:13:58.0 | 3       | 300    | false    | 3  |
    | 2           | 2019-12-31 12:14:58.0 | 4       | 400    | false    | 4  |
    | 2           | 2019-12-31 12:15:58.0 | 5       | 500    | true     | 2  |
    And the following customer transactions are not in the reports:
    | 1           | 2019-11-30 12:12:58.0 | 2       | 200    | false    | 3  |

  Scenario: Successful reporting for merchant
    Given the following transactions:
    | timestamp             | from   | to   | tokenId | amount | isRefund |
    | 2019-12-24 12:11:58.0 | 1      | 3    | 1       | 100    | false    |
    | 2019-11-30 12:12:58.0 | 1      | 3    | 2       | 200    | false    |
    | 2019-12-01 12:13:58.0 | 2      | 3    | 3       | 300    | false    |
    | 2019-12-31 12:14:58.0 | 2      | 4    | 4       | 400    | false    |
    | 2019-12-31 12:15:58.0 | 4      | 2    | 5       | 500    | true     |
    When transaction manager generates monthly merchant reports for 12 2019
    Then the following merchant transactions are in the reports:
    | merchantId  | timestamp             | tokenId | amount | isRefund |
    | 3           | 2019-12-24 12:11:58.0 | 1       | 100    | false    |
    | 3           | 2019-12-01 12:13:58.0 | 3       | 300    | false    |
    | 4           | 2019-12-31 12:14:58.0 | 4       | 400    | false    |
    | 4           | 2019-12-31 12:15:58.0 | 5       | 500    | true     |
    And the following merchant transactions are not in the reports:
    | 3           | 2019-11-30 12:12:58.0 | 2       | 200    | false    |

