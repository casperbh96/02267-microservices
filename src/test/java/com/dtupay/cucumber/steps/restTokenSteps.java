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

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class restTokenSteps {

    URL url2;
    HttpURLConnection con;
    IBusinessLogicForToken t = new BusinessLogicForToken();
    ITokenManagement tokenManager = new TokenManagement();
    Token token;

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

    @Then("^the REST service returns an unused token$")
    public void the_REST_service_returns_an_unused_token() throws Throwable {
        int status = con.getResponseCode();
        assertEquals(200, status);
    }

}
