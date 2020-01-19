package com.dtupay.cucumber.steps;

import com.dtupay.BusinessLogic.BusinessLogicForToken;
import com.dtupay.BusinessLogic.IBusinessLogicForToken;
import com.dtupay.app.ITokenManagement;
import com.dtupay.app.Token;
import com.dtupay.app.TokenManagement;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class restTokenSteps {

    URL url2;
    HttpURLConnection con;
    IBusinessLogicForToken t = new BusinessLogicForToken();
    ITokenManagement tokenManager = new TokenManagement();
    Token token;

    String customerId;
    String uuid;
    String used;

    @Before
    public void Setup() {
        token = t.createToken(1, tokenManager.GetToken(), false);
    }

    @After
    public void cleanUp() {

    }

    @Given("^a customer id (\\d+)$")
    public void a_customer_id(int customerId) throws Throwable {
        url2 = new URL("http://127.0.0.1:8080/token/unused/" + customerId);
    }

    @When("^the token is requested$")
    public void the_token_is_requested() throws Throwable {
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("GET");
    }

    @Given("^a token id (\\d+)$")
    public void a_token_id(int tokenId) throws Throwable {
        url2 = new URL("http://127.0.0.1:8080/token/validation/" + tokenId);
    }

    @When("^the validity check of a token is requested$")
    public void the_validity_check_of_a_token_is_requested() throws Throwable {
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("GET");
    }

    @Then("^the REST service succeeds and returns a (\\d+) status code$")
    public void the_REST_service_succeeds_and_returns_a_status_code(int statusCode) throws Throwable {
        int status = con.getResponseCode();
        assertEquals(statusCode, status);
    }

    @Given("^another token id (\\d+)$")
    public void another_token_id(int tokenId) throws Throwable {
        url2 = new URL("http://127.0.0.1:8080/token/" + tokenId);
    }

    @When("^the update to used is requested$")
    public void the_update_to_used_is_requested() throws Throwable {
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("PUT");
    }

    @Given("^a customer id \"([^\"]*)\", unique identifier \"([^\"]*)\" and a token unused \"([^\"]*)\"$")
    public void a_customer_id_unique_identifier_and_a_token_unused(String customerId, String uuid, String unusedStr) throws Throwable {
        url2 = new URL("http://localhost:8080/token/");
        this.customerId = customerId;
        this.uuid = uuid;
        this.used = unusedStr;
    }

    @When("^the creating of a new token is requested$")
    public void the_creating_of_a_new_token_is_requested() throws Throwable {
        final String POST_PARAMS =
                "{\n" +
                "\r" + "\"customerId\": \""     + customerId    + "\",\n" +
                "\r" + "\"uuid\": \""           + uuid          + "\",\n" +
                "\r" + "\"used\": \""           + used     + "\"" +
                "\n}";
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
    }

    @Then("^the REST service returns the object and a (\\d+) status code$")
    public void the_REST_service_returns_the_object_and_a_status_code(int statusCode) throws Throwable {
        int status = con.getResponseCode();
        assertEquals(statusCode, status);
    }
}
