/*
package com.dtupay.cucumber.steps;

import com.dtupay.DTUPayApp;
import com.dtupay.IDTUPayApp;
import com.dtupay.adapters.customer.model.Customer;
import com.dtupay.adapters.merchant.model.Merchant;
import com.dtupay.adapters.token.model.Token;
import com.dtupay.adapters.bank.IBankAdapter;
import com.dtupay.adapters.bank.exceptions.BankAdapterException;
import com.dtupay.cucumber.utils.Helper;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasicTransferSteps {
    Helper helper;

    IDTUPayApp dtuPayApp;
    IBankAdapter bank;
    ICustomerAdapter customers;
    IMerchantAdapter merchants;
    ITokenAdapter tokens;

    Token token;
    boolean scanSuccessful;

    Customer customer;
    Merchant merchant;


    public BasicTransferSteps(Helper helper) throws BankAdapterException {
        this.helper = helper;
        this.bank = helper.getBank();
        bank.deleteAllCreatedAccounts();
        this.customers = helper.getCustomers();
        this.merchants = helper.getMerchants();
        this.tokens = helper.getTokens();
    }

    @After
    public void cleanUp() throws BankAdapterException {
        bank.deleteAllCreatedAccounts();
    }

    @Given("^customer DTU Pay account \"([^\"]*)\", CPR \"([^\"]*)\", and (\\d+) ([^\"]*) token$")
    public void customerDTUPayAccountIDAndUnusedToken(String name, String cpr, int numOfTokens, String tokenType) throws Throwable {
        switch (tokenType) {
            case "unused":
                customer = helper.createDtuPayCustomer(name, cpr, numOfTokens);
                break;
            case "used":
                customer = helper.createDtuPayCustomerUsedToken(name, cpr, numOfTokens);
                break;
            case "invalid":
                customer = helper.createDtuPayCustomerInvalidToken(name, cpr, numOfTokens);
                break;
        }
    }

    @Given("^merchant DTU Pay account \"([^\"]*)\", CVR \"([^\"]*)\"")
    public void merchantDTUPayAccountIDAndTokens(String name, String cvr) throws Throwable {
        merchant = helper.createDtuPayMerchant(name, cvr);
    }

    @Given("^bank account \"([^\"]*)\", \"([^\"]*)\" with start balance (\\d+)$")
    public void bank_account_with_start_balance(String name, String cpr, int startBalance) throws Throwable {
        helper.createBankAccount(name, cpr, startBalance);
    }

    @When("^the merchant scans the customer's token$")
    public void the_merchant_scans_the_customer_s_token() throws Throwable {
        dtuPayApp = new DTUPayApp(bank, customers, merchants, tokens, new TransactionManager(new TransactionAdapter()));

        customer.setDtuPay(dtuPayApp);
        merchant.setDtuPay(dtuPayApp);
    }

    @Then("^the token is verified as valid$")
    public void TheTokenIsVerifiedAsValid() throws Throwable {
        token = customer.giveToken();
        scanSuccessful = merchant.scanCustomerToken(token);
        assertTrue(scanSuccessful);
    }

    @Then("^token is not found in the DTUPay system$")
    public void TokenIsNotFoundInTheDTUPaySystem() throws Throwable {
        Token token = new Token();
        try {
            tokens.isTokenValid(token.getId());
        } catch (FakeToken e) {
        }
    }

    @Then("^the system detects the token has already been used$")
    public void TheTokenIsNotVerifiedAsUsed() throws Throwable {
        token = customer.giveToken();
        try {
            tokens.isTokenValid(token.getId());
        } catch (TokenAlreadyUsed e) {
        }
    }

    @Then("^the amount (\\d+) is transferred to the merchant$")
    public void theAmountIsTransferredToTheMerchant(int arg1) throws Throwable {
        if (scanSuccessful) {
            merchant.requestTransfer(token, new BigDecimal(arg1), "Transfer");
        }
    }

    @Then("^the balance of the customer is (\\d+)$")
    public void theBalanceOfTheCustomerIs(int arg1) throws Throwable {
        assertEquals(new BigDecimal(arg1), bank.getBalanceByCPR(customer.getCpr()));
    }

    @Then("^the balance of the merchant is (\\d+)$")
    public void theBalanceOfTheMerchantIs(int arg1) throws Throwable {
        assertEquals(new BigDecimal(arg1), bank.getBalanceByCPR(merchant.getCvr()));
    }

}

*/
