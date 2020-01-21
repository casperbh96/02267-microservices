Feature: Rest operations

  Scenario: create a customer
    When we create a customer with name "Dmitry" and cpr "112233-4455"
    Then the customer with name "Dmitry" and cpr "112233-4455" is successfully created

  Scenario: update a customer
    Given we create a customer with name "Dmitry" and cpr "112233-4455"
    And the customer with name "Dmitry" and cpr "112233-4455" is successfully created
    When we update a customer to name "Ib" and cpr "122333-4455"
    Then the customer is successfully updated to name "Ib" and cpr "122333-4455"

  Scenario: create a merchant
    When we create a merchant with name "Ole" and cvr "787878-4455"
    Then the merchant with name "Ole" and cvr "787878-4455" is successfully created

  Scenario: update a merchant
    Given we create a merchant with name "Ole" and cvr "787878-4455"
    And the merchant with name "Ole" and cvr "787878-4455" is successfully created
    When we update a merchant to name "Carl" and cvr "456545-4455"
    Then the merchant is successfully updated to name "Carl" and cvr "456545-4455"

  Scenario: request tokens for customer
    Given we create a customer with name "Olaf" and cpr "112233-4455"
    And the customer with name "Olaf" and cpr "112233-4455" is successfully created
    When the customer requests 3 tokens
    Then the customer receives 3 unused tokens

  Scenario: request tokens for customer
    Given there exist a bank account with cpr "943124-5843" and balance 1000
    And we create a customer with name "Hans" and cpr "943124-5843"
    And the customer with name "Hans" and cpr "943124-5843" is successfully created
    And there exist a bank account with cpr "395873-1343" and balance 0
    And we create a merchant with name "Bruno" and cvr "395873-1343"
    And the merchant with name "Bruno" and cvr "395873-1343" is successfully created
    And the customer requests 1 tokens
    And the customer receives 1 unused tokens
    When a transfer of 100 from the customer to the merchant is done
    Then balance of customer is 900
    And balance of merchant is 100
