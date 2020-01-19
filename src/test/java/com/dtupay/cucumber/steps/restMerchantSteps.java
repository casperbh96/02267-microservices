package com.dtupay.cucumber.steps;

import com.dtupay.BusinessLogic.BusinessLogicForMerchant;
import com.dtupay.BusinessLogic.IBusinessLogicForMerchant;
import com.dtupay.app.Merchant;
import com.dtupay.database.exceptions.MerchantDoesNotExist;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import sun.net.www.protocol.http.HttpURLConnection;

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
    IBusinessLogicForMerchant m = new BusinessLogicForMerchant();
    Merchant merchant;

    @Before
    public void Setup() {
        merchant = m.CreateMerchant("87654321", "Donald the merchant");
    }

    @After
    public void cleanUp() throws MerchantDoesNotExist {
        m.DeleteMerchantByMerchantId(merchant.getId());
    }


    @Given("^merchant with ID (\\d+)$")
    public void merchantWithID(int merchant_id) throws MalformedURLException {
        url2 = new URL("http://localhost:8080/merchant/" + merchant_id);
    }

    @Given("^a new merchant we created$")
    public void aNewMerchantWeCreated() throws MalformedURLException {
        url2 = new URL("http://localhost:8080/merchant/");
    }

    @Given("^the merchant we created$")
    public void theMerchantWeCreated() throws MalformedURLException {
        url2 = new URL("http://localhost:8080/merchant/" + merchant.getId());
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
    }

    @Then("^the rest service returns an error response$")
    public void theRestServiceReturnsAnErrorResponse() throws IOException {
        int status = con.getResponseCode();
        //assertEquals(response.toString(), 200, response.getStatus());
        assertEquals(400, status);
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
                "    \"cvr\": \""+cvr+"\"" + "\n}";
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
        assertEquals(202, status);
    }

    @Then("^the rest service updates it correctly$")
    public void theRestServiceUpdatesItCorrectly() throws IOException {
        int status = con.getResponseCode();
        assertEquals(200, status);
    }

    @When("^the merchant is updated with CVR \"([^\"]*)\", name \"([^\"]*)\"$")
    public void theMerchantIsUpdatedWithCVRName(String cvr, String merchant_name) throws Throwable {
        final String PUT_PARAMS = "{\n" + "\"name\": \""+merchant_name+"\",\r\n" +
                "    \"id\": "+merchant.getId()+",\r\n" +
                "    \"cvr\": \""+cvr+"\"" + "\n}";
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(PUT_PARAMS.getBytes());
    }

    @Given("^we have some merchants in the database$")
    public void weHaveSomeMerchantsInTheDatabase() throws MalformedURLException {
        url2 = new URL("http://localhost:8080/merchant/");
    }

    @When("^we request to see all the merchants$")
    public void weRequestToSeeAllTheMerchants() throws IOException {
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("GET");
    }

    @Then("^the service returns a list with all the merchants$")
    public void theServiceReturnsAListWithAllTheMerchants() throws IOException {
        int status = con.getResponseCode();
        assertEquals(200, status);
    }

}

