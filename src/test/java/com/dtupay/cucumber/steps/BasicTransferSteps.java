package com.dtupay.cucumber.steps;

import com.dtupay.app.Customer;
import com.dtupay.app.DtuPayApp;
import com.dtupay.app.IDtuPayApp;
import com.dtupay.app.Merchant;
import com.dtupay.cucumber.utils.Helper;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.IMerchantAdapter;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dtu.ws.fastmoney.Bank;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class BasicTransferSteps {
    Helper helper;

    List<String> accounts = new ArrayList<>();
    int customerId;
    int merchantId;

    IDtuPayApp dtuPayApp;
    Bank bank;
    ICustomerAdapter customers;
    IMerchantAdapter merchants;

    boolean scanSuccessful;


    public BasicTransferSteps(Helper helper) {
        this.helper = helper;
        this.bank = helper.getBank();
        this.customers = helper.getCustomers();
        this.merchants = helper.getMerchants();
    }

    @Given("^bank account \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" with start balance (\\d+)$")
    public void bank_account_with_start_balance(String arg1, String arg2, String arg3, int arg4) throws Throwable {
        accounts.add(helper.createBankAccount(arg1, arg2, arg3, arg4));
    }

    @Given("^customer DTU Pay account \"([^\"]*)\", ID (\\d+), and (\\d+) unused token$")
    public void customer_DTU_Pay_account_ID_and_unused_token(String arg1, int arg2, int arg3) throws Throwable {
        customerId = helper.createDtuPayCustomer(arg1, arg2, arg3).getId();
    }

    @Given("^merchant DTU Pay account \"([^\"]*)\", ID (\\d+), and (\\d+) tokens$")
    public void merchant_DTU_Pay_account_ID_and_tokens(String arg1, int arg2, int arg3) throws Throwable {
        merchantId = helper.createDtuPayMerchant(arg1, arg2, arg3).getId();
    }

    @When("^the merchant scans the customer's token$")
    public void the_merchant_scans_the_customer_s_token() throws Throwable {
        dtuPayApp = new DtuPayApp(bank, customers, merchants);

        Customer customer = customers.getCustomerByCustomerId(customerId);
        Merchant merchant = merchants.getMerchantByMerchantId(merchantId);

        customer.setDtuPay(dtuPayApp);
        merchant.setDtuPay(dtuPayApp);

        scanSuccessful = merchant.scanCustomerToken(customer.giveToken());
        Assert.assertEquals(true, scanSuccessful);
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
