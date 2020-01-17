package com.dtupay.cucumber.steps;

import com.dtupay.app.Merchant;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.ws.rs.core.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class restMerchantSteps {

    URL url2;
    HttpURLConnection con;
    String merchant_name;
    String cvr;
    String merchant_id;

    @Given("^merchant with ID (\\d+)$")
    public void merchantWithID(int merchant_id) throws MalformedURLException {
        url2 = new URL("http://localhost:8080/merchant/" + merchant_id);
    }

    @When("^the merchant is requested$")
    public void theMerchantIsRequested() throws IOException {
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("GET");
    }

    @Then("^the rest service returns the merchant associated$")
    public void theRestServiceReturnsTheMerchantAssociated() throws IOException {
        int status = con.getResponseCode();
        //assertEquals(response.toString(), 200, response.getStatus());
        assertEquals(200, status);
        System.out.println(status);
    }

    @Then("^the rest service returns an error response$")
    public void theRestServiceReturnsAnErrorResponse() throws IOException {
        int status = con.getResponseCode();
        //assertEquals(response.toString(), 200, response.getStatus());
        assertEquals(500, status);
        System.out.println(status);
    }

    @When("^the merchant is requested to be deleted$")
    public void theMerchantIsRequestedToBeDeleted() throws IOException {
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("DELETE");
    }

    @Then("^the rest service deletes it correctly$")
    public void theRestServiceDeletesItCorrectly() throws IOException {
        int status = con.getResponseCode();
        assertEquals(200, status);
        System.out.println(status);
    }

    @Given("^a new merchant with CVR \"([^\"]*)\", name \"([^\"]*)\"$")
    public void aNewMerchantWithIDNameAndDTUPay(String cvr, String merchant_name) throws Throwable {
        url2 = new URL("http://localhost:8080/merchant/");
        this.cvr = cvr;
        this.merchant_name = merchant_name;
    }

    @When("^the merchant is posted to the service$")
    public void theMerchantIsPostedToTheService() throws IOException {
        final String POST_PARAMS = "{\n" + "\"name\": \""+merchant_name+"\",\r\n" +
                "    \"id\": \""+cvr+"\"" + "\n}";
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
    }

    @Then("^the rest service posts it correctly$")
    public void theRestServicePostsItCorrectly() throws IOException {
        int status = con.getResponseCode();
        assertEquals(200, status);
        System.out.println(status);
    }

    @Then("^the rest service updates it correctly$")
    public void theRestServiceUpdatesItCorrectly() throws IOException {
        int status = con.getResponseCode();
        assertEquals(200, status);
        System.out.println(status);
    }

    @Given("^an existing merchant with ID \"([^\"]*)\"$")
    public void anExistingMerchantWithID(String merchant_id) throws Throwable {
        url2 = new URL("http://localhost:8080/merchant/");
        this.merchant_id = merchant_id;

    }

    @When("^the merchant is updated with CVR \"([^\"]*)\", name \"([^\"]*)\"$")
    public void theMerchantIsUpdatedWithCVRName(String cvr, String merchant_name) throws Throwable {
        final String PUT_PARAMS = "{\n" + "\"name\": \""+merchant_name+"\",\r\n" +
                "    \"id\": \""+merchant_id+"\",\r\n" +
                "    \"cvr\": \""+cvr+"\"" + "\n}";
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(PUT_PARAMS.getBytes());
    }
}

