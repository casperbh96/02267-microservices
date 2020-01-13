package com.dtupay.cucumber.steps;

import com.dtupay.app.*;
import com.dtupay.bank.IBankAdapter;
import com.dtupay.cucumber.utils.Helper;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.IMerchantAdapter;
import com.dtupay.database.ITokenAdapter;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dtu.ws.fastmoney.Bank;
import org.junit.Assert;

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


    public BasicTransferSteps(Helper helper) {
        this.helper = helper;
        this.bank = helper.getBank();
        this.customers = helper.getCustomers();
        this.merchants = helper.getMerchants();
        this.tokens = helper.getTokens();
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

        Customer customer = customers.getCustomerByCustomerId(customerId);
        Merchant merchant = merchants.getMerchantByMerchantId(merchantId);

        customer.setDtuPay(dtuPayApp);
        merchant.setDtuPay(dtuPayApp);

        token = customer.giveToken();
        scanSuccessful = merchant.scanCustomerToken(token);
        assertTrue(scanSuccessful);
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
}
