Feature: REST API calls for Token

  Scenario: Get unused token SUCCESS
    Given a customer id 1
    When the token is requested
    Then the REST service returns an unused token

  Scenario: Get unused token FAILURE
    Given a customer id 2
    When the token is requested
    Then the REST service fails and returns a 400 status code

  Scenario: Get validity of token SUCCESS
    Given a token id 1
    When the validity check of a token is requested
    Then the REST service returns the token is valid

  Scenario: Get validity of token FAILURE
    Given a token id 2
    When the validity check of a token is requested
    Then the REST service returns the token is invalid

  Scenario: Set a token from unused to used SUCCESS
    Given a token id 3
    When the update to used is requested
    Then the REST service returns a 200 status code

  Scenario: Set a token from unused to used FAILURE
    Given a token id 4
    When the update to used is requested
    Then the REST service returns a 400 status code

  Scenario: Create a new token SUCCESS
    Given a customer id 3, unique identifier 1 and whether the token is used false
    When the creating of a new token is requested
    Then the REST service returns a 200 status code

  Scenario: Create a new token FAILURE
    Given a customer id 4, unique identifier 2 and whether the token is used true
    When the creating of a new token is requested
    Then the REST service returns a 400 status code