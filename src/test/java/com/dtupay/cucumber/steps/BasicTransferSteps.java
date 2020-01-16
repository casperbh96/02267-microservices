package com.dtupay.cucumber.steps;

import com.dtupay.app.*;
import com.dtupay.bank.IBankAdapter;
import com.dtupay.cucumber.utils.Helper;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.IMerchantAdapter;
import com.dtupay.database.ITokenAdapter;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasicTransferSteps {
    Helper helper;

    String customerId;
    String merchantId;

    IDtuPayApp dtuPayApp;
    IBankAdapter bank;
    ICustomerAdapter customers;
    IMerchantAdapter merchants;
    ITokenAdapter tokens;

    Token token;
    boolean scanSuccessful;

    Customer customer;
    Merchant merchant;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();


    public BasicTransferSteps(Helper helper) {
        this.helper = helper;
        this.bank = helper.getBank();
        this.customers = helper.getCustomers();
        this.merchants = helper.getMerchants();
        this.tokens = helper.getTokens();
    }

    @Before
    public void deleteeAccount() throws Exception {
        bank.deleteAllAccounts();
    }

    @Given("^customer DTU Pay account \"([^\"]*)\", ID \"([^\"]*)\", and (\\d+) unused token$")
    public void customerDTUPayAccountIDAndUnusedToken(String name, String cpr, int numOfTokens) throws Throwable {
        customerId = helper.createDtuPayCustomer(name, cpr, numOfTokens).getId();
    }

    @Given("^merchant DTU Pay account \"([^\"]*)\", ID \"([^\"]*)\"")
    public void merchantDTUPayAccountIDAndTokens(String name, String cpr) throws Throwable {
        merchantId = helper.createDtuPayMerchant(name, cpr).getId();
    }

    @Given("^bank account \"([^\"]*)\", \"([^\"]*)\" with start balance (\\d+)$")
    public void bank_account_with_start_balance(String name, String cpr, int startBalance) throws Throwable {
        helper.createBankAccount(name, cpr, startBalance);
    }

    @When("^the merchant scans the customer's token$")
    public void the_merchant_scans_the_customer_s_token() throws Throwable {
        dtuPayApp = new DtuPayApp(bank, customers, merchants, tokens);

        customer = customers.getCustomerByCustomerId(customerId);
        merchant = merchants.getMerchantByMerchantId(merchantId);

        customer.setDtuPay(dtuPayApp);
        merchant.setDtuPay(dtuPayApp);
    }

    @Then("^the token is verified as valid$")
    public void TheTokenIsVerifiedIsValid() throws Throwable {
        token = customer.giveToken();
        assertTrue(merchant.scanCustomerToken(token));
    }

    @Then("^token is not found in the DTUPay system$")
    public void TokenIsNotFoundInTheDTUPaySystem() throws Throwable {
        Token token = new Token();
        expectedEx.expect(FakeToken.class);
        tokens.checkToken(token);
    }

    @Then("^the system detects the token has already been used$")
    public void the_system_detects_the_token_has_already_been_used() throws Throwable {
        token = customer.giveToken();
        expectedEx.expect(TokenAlreadyUsed.class);
        tokens.checkToken(token);
    }

    @Then("^the amount (\\d+) is transferred to the merchant$")
    public void the_amount_is_transferred_to_the_merchant(int arg1) throws Throwable {
        if (scanSuccessful) {
            Merchant merchant = merchants.getMerchantByMerchantId(merchantId);
            merchant.requestTransfer(token, new BigDecimal(arg1), "Transfer");
        }
    }

    @Then("^the balance of the customer is (\\d+)$")
    public void the_balance_of_the_customer_is(int arg1) throws Throwable {
        assertEquals(new BigDecimal(arg1), bank.getBalanceByCPR(customerId));
    }

    @Then("^the balance of the merchant is (\\d+)$")
    public void the_balance_of_the_merchant_is(int arg1) throws Throwable {
        assertEquals(new BigDecimal(arg1), bank.getBalanceByCPR(merchantId));
    }

    @Given("^customer DTU Pay account \"([^\"]*)\", with ID \"([^\"]*)\", and (\\d+) invalid token$")
    public void ThecustomerDTUPayAccountIDAndInvalidToken(String name, String cpr, int numOfTokens) throws Throwable {
        customerId = helper.createDtuPayCustomerUsedToken(name, cpr, numOfTokens).getId();

    }

    @When("^the merchant scans the customer's invalid token$")
    public void The_merchant_scans_the_customer_s_invalid_token() throws Throwable {
        dtuPayApp = new DtuPayApp(bank, customers, merchants, tokens);

        customer = customers.getCustomerByCustomerId(customerId);
        merchant = merchants.getMerchantByMerchantId(merchantId);

        customer.setDtuPay(dtuPayApp);
        merchant.setDtuPay(dtuPayApp);
    }
}


