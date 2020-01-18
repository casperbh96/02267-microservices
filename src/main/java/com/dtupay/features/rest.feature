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

