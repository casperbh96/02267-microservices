package com.dtupay.cucumber.steps;

import com.dtupay.adapters.bank.BankAdapter;
import com.dtupay.adapters.bank.IBankAdapter;
import com.dtupay.adapters.customer.model.Customer;
import com.dtupay.adapters.merchant.model.Merchant;
import com.dtupay.adapters.token.model.Token;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BasicRestPointSteps {
    private URL url;
    HttpURLConnection connection;

    IBankAdapter bankAdapter = new BankAdapter();

    Customer customer;
    Merchant merchant;

    private final String CREATE_CUSTOMER_URL = "http://localhost:8080/customer";
    private final String UPDATE_CUSTOMER_URL = "http://localhost:8080/customer";
    private final String GET_NEW_TOKENS_FOR_CUSTOMER_URL = "http://localhost:8080/newTokens";
    private final String GET_MONTHLY_REPORT_FOR_CUSTOMER_URL = "http://localhost:8080/customer/transactions";

    private final String CREATE_MERCHANT_URL = "http://localhost:8080/merchant";
    private final String UPDATE_MERCHANT_URL = "http://localhost:8080/merchant";
    private final String TRANSFER_MONEY_URL = "http://localhost:8080/transaction";
    private final String GET_MONTHLY_REPORT_FOR_MERCHANT_URL = "http://localhost:8080/merchant/transactions";

    @After
    public void bankCleanup() throws Throwable {
        bankAdapter.deleteAllCreatedAccounts();
    }

    @When("^we create a customer with name \"([^\"]*)\" and cpr \"([^\"]*)\"$")
    public void we_create_a_customer_with_name_and_cpr(String name, String cpr) throws Throwable {
        url = new URL(CREATE_CUSTOMER_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        final String jsonRequest = "{\"cpr\":\"" + cpr + "\",\"name\":\"" + name + "\"}";
        OutputStream os = connection.getOutputStream();
        os.write(jsonRequest.getBytes());
        os.flush();

        connection.getResponseCode();
    }

    @Then("^the customer with name \"([^\"]*)\" and cpr \"([^\"]*)\" is successfully created$")
    public void the_customer_with_name_and_cpr_is_successfully_created(String name, String cpr) throws Throwable {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String jsonRaw = "";
        String line;
        while ((line = in.readLine()) != null) {
            jsonRaw += line + "/n";
        }
        customer = new Customer(new JSONObject(jsonRaw));
        Assert.assertEquals(name, customer.getName());
        Assert.assertEquals(cpr, customer.getCpr());
    }


    @When("^we update a customer to name \"([^\"]*)\" and cpr \"([^\"]*)\"$")
    public void we_update_a_customer_to_name_and_cpr(String name, String cpr) throws Throwable {
        url = new URL(UPDATE_CUSTOMER_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        final String jsonRequest = "{\"id\":\"" + customer.getId() + "\",\"cpr\":\"" + cpr + "\",\"name\":\"" + name + "\"}";
        OutputStream os = connection.getOutputStream();
        os.write(jsonRequest.getBytes());
        os.flush();

        connection.getResponseCode();
    }

    @Then("^the customer is successfully updated to name \"([^\"]*)\" and cpr \"([^\"]*)\"$")
    public void the_customer_is_successfully_updated_to_name_and_cpr(String name, String cpr) throws Throwable {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String jsonRaw = "";
        String line;
        while ((line = in.readLine()) != null) {
            jsonRaw += line + "\n";
        }
        Customer c = new Customer(new JSONObject(jsonRaw));
        Assert.assertEquals(customer.getId(), c.getId());
        Assert.assertEquals(name, c.getName());
        Assert.assertEquals(cpr, c.getCpr());
    }

    @When("^we create a merchant with name \"([^\"]*)\" and cvr \"([^\"]*)\"$")
    public void we_create_a_merchant_with_name_and_cvr(String name, String cvr) throws Throwable {
        url = new URL(CREATE_MERCHANT_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        final String jsonRequest = "{\"cvr\":\"" + cvr + "\",\"name\":\"" + name + "\"}";
        OutputStream os = connection.getOutputStream();
        os.write(jsonRequest.getBytes());
        os.flush();

        connection.getResponseCode();
    }

    @Then("^the merchant with name \"([^\"]*)\" and cvr \"([^\"]*)\" is successfully created$")
    public void the_merchant_with_name_and_cvr_is_successfully_created(String name, String cvr) throws Throwable {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String jsonRaw = "";
        String line;
        while ((line = in.readLine()) != null) {
            jsonRaw += line + "/n";
        }
        merchant = new Merchant(new JSONObject(jsonRaw));
        Assert.assertEquals(name, merchant.getName());
        Assert.assertEquals(cvr, merchant.getCvr());
    }

    @When("^we update a merchant to name \"([^\"]*)\" and cvr \"([^\"]*)\"$")
    public void we_update_a_merchant_to_name_and_cvr(String name, String cvr) throws Throwable {
        url = new URL(UPDATE_MERCHANT_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        final String jsonRequest = "{\"id\":\"" + merchant.getId() + "\",\"cvr\":\"" + cvr + "\",\"name\":\"" + name + "\"}";
        OutputStream os = connection.getOutputStream();
        os.write(jsonRequest.getBytes());
        os.flush();

        connection.getResponseCode();
    }

    @Then("^the merchant is successfully updated to name \"([^\"]*)\" and cvr \"([^\"]*)\"$")
    public void the_merchant_is_successfully_updated_to_name_and_cvr(String name, String cvr) throws Throwable {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String jsonRaw = "";
        String line;
        while ((line = in.readLine()) != null) {
            jsonRaw += line + "\n";
        }
        Merchant m = new Merchant(new JSONObject(jsonRaw));
        Assert.assertEquals(merchant.getId(), m.getId());
        Assert.assertEquals(name, m.getName());
        Assert.assertEquals(cvr, m.getCvr());
    }


    @When("^the customer requests (\\d+) tokens$")
    public void the_customer_requests_tokens(int numOfTokens) throws Throwable {
        url = new URL(GET_NEW_TOKENS_FOR_CUSTOMER_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        final String jsonRequest = "{\"customerId\":" + customer.getId() + ",\"numberOfTokens\":" + numOfTokens + "}";
        OutputStream os = connection.getOutputStream();
        os.write(jsonRequest.getBytes());
        os.flush();

        connection.getResponseCode();
    }

    @Then("^the customer receives (\\d+) unused tokens$")
    public void the_customer_receives_unused_tokens(int numOfTokens) throws Throwable {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String jsonRaw = "";
        String line;
        while ((line = in.readLine()) != null) {
            jsonRaw += line + "\n";
        }
        JSONArray jsonArray = new JSONArray(jsonRaw);
        Assert.assertEquals(numOfTokens, jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            Token token = new Token(json);
            Assert.assertEquals(customer.getId(), token.getCustomerId());
            Assert.assertFalse(token.getUsed());
            customer.getTokens().add(token);
        }
    }

    @Given("^there exist a bank account with cpr \"([^\"]*)\" and balance (\\d+)$")
    public void there_exist_a_bank_account_with_cpr_and_balance(String cpr, int balance) throws Throwable {
        bankAdapter.createAccount("Test Acc", cpr, BigDecimal.valueOf(balance));
    }

    @When("^a transfer of (\\d+) from the customer to the merchant is done$")
    public void a_transfer_of_from_the_customer_to_the_merchant_is_done(int amount) throws Throwable {
        url = new URL(TRANSFER_MONEY_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        final String jsonRequest = "{\"merchantId\":" + merchant.getId() + ",\"token\":" + customer.getUnusedToken().toJsonString() + ",\"amount\":" + amount + ",\"description\":\" \"}";
        OutputStream os = connection.getOutputStream();
        os.write(jsonRequest.getBytes());
        os.flush();

        connection.getResponseCode();
    }

    @Then("^balance of customer is (\\d+)$")
    public void balance_of_customer_is(int newBalance) throws Throwable {
        Assert.assertEquals(BigDecimal.valueOf(newBalance), bankAdapter.getBalanceByCPR(customer.getCpr()));
    }

    @Then("^balance of merchant is (\\d+)$")
    public void balance_of_merchant_is(int newBalance) throws Throwable {
        Assert.assertEquals(BigDecimal.valueOf(newBalance), bankAdapter.getBalanceByCPR(merchant.getCvr()));
    }
}
