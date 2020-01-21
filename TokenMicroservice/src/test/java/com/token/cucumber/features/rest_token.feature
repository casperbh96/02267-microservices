# @author Casper

Feature: REST API calls for Token

  Scenario: Get unused token SUCCESS
    Given a customer id 1
    When the token is requested
    Then the REST service succeeds and returns a 200 status code

  Scenario: Get validity of token SUCCESS
    Given a token id 1
    When the validity check of a token is requested
    Then the REST service succeeds and returns a 200 status code

  Scenario: Set a token from unused to used SUCCESS
    Given another token id 2
    When the update to used is requested
    Then the REST service succeeds and returns a 200 status code

  Scenario: Create a new token SUCCESS
    Given a customer id "2", unique identifier "ee467f55-a288-4d2b-b5dd-aae6dae62137" and a token unused "false"
    When the creating of a new token is requested
    Then the REST service returns the object and a 200 status code