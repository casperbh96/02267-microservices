package com.dtupay.cucumber.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BasicTransferTest {
    @Given("^bank account \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" with start balance (\\d+)$")
    public void bank_account_with_start_balance(String arg1, String arg2, String arg3, int arg4) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^customer DTU Pay account \"([^\"]*)\", ID (\\d+), and (\\d+) unused token$")
    public void customer_DTU_Pay_account_ID_and_unused_token(String arg1, int arg2, int arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^merchant DTU Pay account \"([^\"]*)\", ID (\\d+), and (\\d+) tokens$")
    public void merchant_DTU_Pay_account_ID_and_tokens(String arg1, int arg2, int arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^the merchant scans the customer's token$")
    public void the_merchant_scans_the_customer_s_token() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the amount (\\d+) is transferred to the merchant$")
    public void the_amount_is_transferred_to_the_merchant(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the balance of the customer is (\\d+)$")
    public void the_balance_of_the_customer_is(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the balance of the merchant is (\\d+)$")
    public void the_balance_of_the_merchant_is(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
