package com.token.cucumber.steps;

import com.token.app.Token;
import com.token.manager.ITokenManager;
import com.token.manager.TokenManager;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * @author Casper
 * this class asserts the basic rest operations using cucumber testing
 */
public class RestTokenSteps {

    URL url2;
    HttpURLConnection con;
    ITokenManager t = new TokenManager();
    Token token;

    String customerId;
    String uuid;
    String used;

    /**
     * setting up token for tests
     */
    @Before
    public void Setup() {
        token = t.createToken(1, t.getToken(), false);
    }

    @After
    public void cleanUp() {
    }

    /**
     * given a customer with some customer id
     *
     * @param customerId
     * @throws Throwable
     */
    @Given("^a customer id (\\d+)$")
    public void a_customer_id(int customerId) throws Throwable {
        url2 = new URL("http://localhost:8082/token/unused/" + customerId);
    }

    /**
     * checks for the connection when token is requested by the customer and uses GET request
     *
     * @throws Throwable
     */
    @When("^the token is requested$")
    public void the_token_is_requested() throws Throwable {
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("GET");
    }

    /**
     * given a token with some token id
     *
     * @param tokenId
     * @throws Throwable
     */
    @Given("^a token id (\\d+)$")
    public void a_token_id(int tokenId) throws Throwable {
        url2 = new URL("http://localhost:8082/token/" + tokenId);
    }

    /**
     * checks for the validity of token with GET request
     *
     * @throws Throwable
     */
    @When("^the validity check of a token is requested$")
    public void the_validity_check_of_a_token_is_requested() throws Throwable {
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("GET");
    }

    /**
     * checks if the rest service succeeds and returns the status code
     *
     * @param statusCode
     * @throws Throwable
     */
    @Then("^the REST service succeeds and returns a (\\d+) status code$")
    public void the_REST_service_succeeds_and_returns_a_status_code(int statusCode) throws Throwable {
        int status = con.getResponseCode();
        assertEquals(statusCode, status);
    }

    /**
     * test for another token id
     *
     * @param tokenId
     * @throws Throwable
     */
    @Given("^another token id (\\d+)$")
    public void another_token_id(int tokenId) throws Throwable {
        url2 = new URL("http://localhost:8082/token/" + tokenId);
    }

    /**
     * checks if udpate the token to used method is working
     *
     * @throws Throwable
     */
    @When("^the update to used is requested$")
    public void the_update_to_used_is_requested() throws Throwable {
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("PUT");
    }

    /**
     * given that we have one customer with customer id and one unused token
     *
     * @param customerId
     * @param uuid
     * @param unusedStr
     * @throws Throwable
     */
    @Given("^a customer id \"([^\"]*)\", unique identifier \"([^\"]*)\" and a token unused \"([^\"]*)\"$")
    public void a_customer_id_unique_identifier_and_a_token_unused(String customerId, String uuid, String unusedStr) throws Throwable {
        url2 = new URL("http://localhost:8082/token/");
        this.customerId = customerId;
        this.uuid = uuid;
        this.used = unusedStr;
    }

    /**
     * checks if creation of new token function is working using rest
     *
     * @throws Throwable
     */
    @When("^the creating of a new token is requested$")
    public void the_creating_of_a_new_token_is_requested() throws Throwable {
        final String POST_PARAMS =
                "{\n" +
                        "\r" + "\"customerId\": \"" + customerId + "\",\n" +
                        "\r" + "\"uuid\": \"" + uuid + "\",\n" +
                        "\r" + "\"used\": \"" + used + "\"" +
                        "\n}";
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
    }

    /**
     * asserts that rest service is returning the object and accepted status code
     *
     * @param statusCode
     * @throws Throwable
     */
    @Then("^the REST service returns the object and a (\\d+) status code$")
    public void the_REST_service_returns_the_object_and_a_status_code(int statusCode) throws Throwable {
        int status = con.getResponseCode();
        assertEquals(statusCode, status);
    }
}
