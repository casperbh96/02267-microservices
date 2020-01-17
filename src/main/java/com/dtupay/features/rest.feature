Feature: Basic rest operations
  Scenario: get merchant successful
    Given merchant with ID 1
    When the merchant is requested
    Then the rest service returns the merchant associated

  Scenario: get merchant unsuccessful
    Given merchant with ID 28
    When the merchant is requested
    Then the rest service returns an error response

  Scenario: delete merchant successful
    Given merchant with ID 1
    When the merchant is requested to be deleted
    Then the rest service deletes it correctly

  Scenario: delete merchant unsuccessful
    Given merchant with ID 1
    When the merchant is requested to be deleted
    Then the rest service returns an error response

  Scenario: post merchant successful
    Given a new merchant with CVR "1", name "DTU Canteen"
    When the merchant is posted to the service
    Then the rest service posts it correctly

  Scenario: post merchant unsuccessful
    Given a new merchant with CVR "1", name "DTU Canteen"
    When the merchant is posted to the service
    Then the rest service returns an error response

  Scenario: update merchant successful
    Given an existing merchant with ID "1"
    When the merchant is updated with CVR "test", name "DTU Skylab"
    Then the rest service updates it correctly

  Scenario: update merchant unsuccessful
    Given an existing merchant with ID "1"
    When the merchant is updated with CVR "test1", name "DTU Skylab"
    Then the rest service returns an error response

