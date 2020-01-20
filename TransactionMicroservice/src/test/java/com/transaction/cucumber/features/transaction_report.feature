Feature: Monthly reporting of transactions
  Scenario: Successful reporting for customer
    Given the following transactions:
    | timestamp           | from   | to   | tokenId | amount | isRefund |
    | 2019-12-24 12:11:58 | 1      | 3    | 1       | 200    | false    |
    | 2019-11-30 12:11:58 | 1      | 3    | 2       | 200    | false    |
    | 2019-12-01 12:11:58 | 2      | 3    | 3       | 200    | false    |
    | 2019-12-31 12:11:58 | 2      | 4    | 4       | 200    | false    |
    | 2019-12-31 12:11:58 | 4      | 2    | 4       | 200    | true     |
    When transaction manager generates monthly customer reports for 12 2019
    Then the following customer reports will be generated:
    | customerId  | transactionsInReport |
    | 1           | 1                    |
    | 2           | 3                    |

  Scenario: Successful reporting for merchant
    Given the following transactions:
    | timestamp           | from   | to   | tokenId | amount | isRefund |
    | 2019-12-24 12:11:58 | 1      | 3    | 1       | 200    | false    |
    | 2019-11-30 12:11:58 | 1      | 3    | 2       | 200    | false    |
    | 2019-12-01 12:11:58 | 2      | 3    | 3       | 200    | false    |
    | 2019-12-31 12:11:58 | 2      | 4    | 4       | 200    | false    |
    | 2019-12-31 12:11:58 | 4      | 2    | 4       | 200    | true     |
    When transaction manager generates monthly merchant reports for 12 2019
    Then the following merchant reports will be generated:
    | merchantId  | transactionsInReport |
    | 3           | 2                    |
    | 4           | 2                    |

