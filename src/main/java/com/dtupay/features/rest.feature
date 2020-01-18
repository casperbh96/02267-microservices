
Feature: Basic rest

  Scenario: Get customer
    Given customer with 1 as Id
    When the customer is requested
    Then the rest service will send ok 200 http status

  Scenario: Get customer with a wrong id
    Given customer with 2131231 as id
    When the customer is requestedTest
    Then the rest service will send not found 404 http status

  Scenario: Create a new customer
    Given a customer named "TrustMeItWillWork" and cpr "433330"
    When the customer is posted to the service
    Then the rest service will post ok 200 http status
