package com.dtupay.cucumber.steps;

import com.dtupay.app.*;
import com.dtupay.bank.IBankAdapter;
import com.dtupay.cucumber.utils.Helper;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.IMerchantAdapter;
import com.dtupay.database.ITokenAdapter;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UsedTokenSteps {
    Helper helper;

    List<String> accounts = new ArrayList<>();
    String customerId;
    String merchantId;

    IDtuPayApp dtuPayApp;
    IBankAdapter bank;
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

    @Given("^bank account \"([^\"]*)\", with ID \"([^\"]*)\" with starting balance (\\d+)$")
    public void bank_account_with_starting_balance(String name, String cpr, int startingBalance) throws Throwable {
        helper.createBankAccount(name, cpr, startingBalance);
    }

    @Given("^customer DTU Pay account \"([^\"]*)\", with ID \"([^\"]*)\", and (\\d+) used token$")
    public void customerDTUPayAccountIDAndUsedToken(String name, String cpr, int numOfTokens) throws Throwable {
        customerId = helper.createDtuPayCustomerUsedToken(name, cpr, numOfTokens).getId();

    }

    @Given("^merchant DTU Pay account \"([^\"]*)\", with ID \"([^\"]*)\"")
    public void merchantDTUPayAccountIDAndUsedTokens(String name, String cpr) throws Throwable {
        merchantId = helper.createDtuPayMerchant(name, cpr).getId();
    }

    @When("^the merchant scans the customer's used token$")
    public void the_merchant_scans_the_customer_s_used_token() throws Throwable {
        dtuPayApp = new DtuPayApp(bank, customers, merchants, tokens);

        customer = customers.getCustomerByCustomerId(customerId);
        merchant = merchants.getMerchantByMerchantId(merchantId);

        customer.setDtuPay(dtuPayApp);
        merchant.setDtuPay(dtuPayApp);
    }


}
