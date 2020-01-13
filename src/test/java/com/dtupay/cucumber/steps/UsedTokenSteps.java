package com.dtupay.cucumber.steps;

import com.dtupay.app.*;
import com.dtupay.cucumber.utils.Helper;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.IMerchantAdapter;
import com.dtupay.database.ITokenAdapter;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dtu.ws.fastmoney.Bank;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UsedTokenSteps {
    Helper helper;

    List<String> accounts = new ArrayList<>();
    String customerId;
    String merchantId;

    IDtuPayApp dtuPayApp;
    Bank bank;
    ICustomerAdapter customers;
    IMerchantAdapter merchants;
    ITokenAdapter tokens;

    Token token;
    boolean scanUnsuccessful;

    Customer customer;
    Merchant merchant;


    public UsedTokenSteps(Helper helper) {
        this.helper = helper;
        this.bank = helper.getBank();
        this.customers = helper.getCustomers();
        this.merchants = helper.getMerchants();
        this.tokens = helper.getTokens();
    }

    @Given("^bank account \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" with starting balance (\\d+)$")
    public void bank_account_with_starting_balance(String arg1, String arg2, String arg3, int arg4) throws Throwable {
        accounts.add(helper.createBankAccount(arg1, arg2, arg3, arg4));
    }

    @Given("^customer DTU Pay account \"([^\"]*)\", ID \"([^\"]*)\", and (\\d+) used token$")
    public void customerDTUPayAccountIDAndUsedToken(String arg1, String arg2, int arg3) throws Throwable {
        customerId = helper.createDtuPayCustomerUsedToken(arg1, arg2, arg3).getId();

    }

    @Given("^merchant DTU Pay account \"([^\"]*)\", ID \"([^\"]*)\", and (\\d+) used tokens$")
    public void merchantDTUPayAccountIDAndUsedTokens(String arg1, String arg2, int arg3) throws Throwable {
        merchantId = helper.createDtuPayMerchant(arg1, arg2, arg3).getId();
    }

    @When("^the merchant scans the customer's used token$")
    public void the_merchant_scans_the_customer_s_used_token() throws Throwable {
        dtuPayApp = new DtuPayApp(bank, customers, merchants, tokens);

        customer = customers.getCustomerByCustomerId(customerId);
        merchant = merchants.getMerchantByMerchantId(merchantId);

        customer.setDtuPay(dtuPayApp);
        merchant.setDtuPay(dtuPayApp);
    }

    @Then("^the system detects the token has already been used$")
    public void the_system_detects_the_token_has_already_been_used() throws Throwable {
        token = customer.giveToken();
        scanUnsuccessful = merchant.scanCustomerToken(token);
        assertEquals(false, scanUnsuccessful);
    }

}
