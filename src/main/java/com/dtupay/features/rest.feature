Feature: Basic rest operations
  Scenario: get merchant successful
    Given a new merchant we created
    When the merchant is requested
    Then the rest service returns the merchant associated

  Scenario: get merchant unsuccessful
    Given merchant with ID 400000
    When the merchant is requested
    Then the rest service returns an error response

#  Scenario: update merchant successful
#    Given a new merchant we created
#    When the merchant is updated with CVR "test", name "DTU Skylab"
#    Then the rest service updates it correctly

  Scenario: delete merchant successful
    Given a new merchant we created
    When the merchant is requested to be deleted
    Then the rest service deletes it correctly

  Scenario: post merchant successful
    Given a new merchant with CVR "2", name "DTU Canteen"
    When the merchant is posted to the service
    Then the rest service posts it correctly

  Scenario: Get customer successful
    Given a new customer we created
    When the customer is requested
    Then the rest service returns the customer associated

  Scenario: Get customer unsuccessful
    Given customer with ID 400000
    When the customer is requested
    Then the rest service will send not found 400 http status

#  Scenario: update customer successful
#    Given a new customer we created
#    When the customer is updated with CPR "test", name "DTU Skylab"
#    Then the rest service updates customer correctly

  Scenario: delete customer successful
    Given a new customer we created
    When the customer is requested to be deleted
    Then the rest service deletes customer correctly

  Scenario: post customer successful
    Given a new customer with CPR "2", name "DTU Canteen"
    When the customer is posted to the service
    Then the rest service posts customer correctly
