Feature: Basic rest operations
  Scenario: get all merchants
    Given we have some merchants in the database
    When we request to see all the merchants
    Then the service returns a list with all the merchants

  Scenario: get merchant successful
    Given a new merchant we created
    When the merchant is requested
    Then the rest service returns the merchant associated

  Scenario: get merchant unsuccessful
    Given merchant with ID 400000
    When the merchant is requested
    Then the rest service returns an error response

  Scenario: update merchant successful
    Given a new merchant we created
    When the merchant is updated with CVR "987654321", name "DTU's best merchant"
    Then the rest service updates it correctly

  Scenario: delete merchant successful
    Given the merchant we created
    When the merchant is requested to be deleted
    Then the rest service deletes it correctly

  Scenario: post merchant successful
    Given a new merchant with CVR "2", name "DTU Sennepsmarken"
    When the merchant is posted to the service
    Then the rest service posts it correctly
