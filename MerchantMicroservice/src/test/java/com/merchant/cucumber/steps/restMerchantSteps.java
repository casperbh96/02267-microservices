package com.merchant.cucumber.steps;

import com.merchant.manager.MerchantManager;
import com.merchant.manager.IMerchantManager;
import com.merchant.app.Merchant;
import com.merchant.database.exceptions.MerchantDoesNotExist;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;


/**
 * this class asserts the basic rest operations using cucumber testing
 */

public class restMerchantSteps {

    URL url2;
    HttpURLConnection con;
    String merchant_name;
    String cvr;
    IMerchantManager m = new MerchantManager();
    Merchant merchant;

    /**
     * setting up merchant for tests
     */
    @Before
    public void Setup() {
        merchant = m.createMerchant("87654321", "Donald the merchant");
    }

    /**
     * checks for deletion of merchant
     * @throws MerchantDoesNotExist
     */
    @After
    public void cleanUp() throws MerchantDoesNotExist {
        m.deleteMerchantByMerchantId(merchant.getId());
    }

    /**
     * given that merchant has id
     * @param customerID
     * @throws MalformedURLException
     */
    @Given("^merchant with ID (\\d+)$")
    public void merchantWithID(int merchant_id) throws MalformedURLException {
        url2 = new URL("http://localhost:8084/merchant/" + merchant_id);
    }

    /**
     * given that a new merchant is created
     * @throws MalformedURLException
     */

    @Given("^a new merchant we created$")
    public void aNewMerchantWeCreated() throws MalformedURLException {
        url2 = new URL("http://localhost:8084/merchant/");
    }


    @Given("^the merchant we created$")
    public void theMerchantWeCreated() throws MalformedURLException {
        url2 = new URL("http://localhost:8084/merchant/" + merchant.getId());
    }

    /**
     * checks if it returns the requested merchant
     * @throws IOException
     */
    @When("^the merchant is requested$")
    public void theMerchantIsRequested() throws IOException {
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("GET");
    }

    /**
     * asserts if rest service is giving the right merchant
     * @throws IOException
     */
    @Then("^the rest service returns the merchant associated$")
    public void theRestServiceReturnsTheMerchantAssociated() throws IOException {
        int status = con.getResponseCode();
        //assertEquals(response.toString(), 200, response.getStatus());
        assertEquals(200, status);
    }

    /**
     * checks if rest service is returning an error response
     * @throws IOException
     */
    @Then("^the rest service returns an error response$")
    public void theRestServiceReturnsAnErrorResponse() throws IOException {
        int status = con.getResponseCode();
        //assertEquals(response.toString(), 200, response.getStatus());
        assertEquals(400, status);
    }

    /**
     * checks for the deletion request for merchant
     * @throws IOException
     */
    @When("^the merchant is requested to be deleted$")
    public void theMerchantIsRequestedToBeDeleted() throws IOException {
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("DELETE");
    }

    /**
     * checks if rest service is deleting the merchant correctly
     * @throws IOException
     */
    @Then("^the rest service deletes it correctly$")
    public void theRestServiceDeletesItCorrectly() throws IOException {
        int status = con.getResponseCode();
        assertEquals(200, status);
    }

    /**
     * given a merchant with cpr
     * @param cvr
     * @param merchant_name
     * @throws Throwable
     */
    @Given("^a new merchant with CVR \"([^\"]*)\", name \"([^\"]*)\"$")
    public void aNewMerchantWithIDNameAndDTUPay(String cvr, String merchant_name) throws Throwable {
        url2 = new URL("http://localhost:8084/merchant/");
        this.cvr = cvr;
        this.merchant_name = merchant_name;
    }

    /**
     * checks if the POST request is working to update the merchant
     * @throws IOException
     */
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

    /**
     * asserts true if update operation is working fine using POST request
     * @throws IOException
     */
    @Then("^the rest service posts it correctly$")
    public void theRestServicePostsItCorrectly() throws IOException {
        int status = con.getResponseCode();
        assertEquals(202, status);
    }

    /**
     * asserts true if update operation is working fine
     * @throws IOException
     */
    @Then("^the rest service updates it correctly$")
    public void theRestServiceUpdatesItCorrectly() throws IOException {
        int status = con.getResponseCode();
        assertEquals(200, status);
    }

    /**
     * checks connection for merchant update using CPR
     * @param cpr
     * @param merchant_name
     * @throws Throwable
     */
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

    /**
     * checks for given condition about merchant being in database
     * @throws MalformedURLException
     */
    @Given("^we have some merchants in the database$")
    public void weHaveSomeMerchantsInTheDatabase() throws MalformedURLException {
        url2 = new URL("http://localhost:8084/merchant/");
    }

    /**
     * checks for when we request to see all the merchants
     * @throws IOException
     */
    @When("^we request to see all the merchants$")
    public void weRequestToSeeAllTheMerchants() throws IOException {
        con = (HttpURLConnection) url2.openConnection();
        con.setRequestMethod("GET");
    }

    /**
     * checks for assertion condition if rest service is returning all the merchants or not
     * @throws IOException
     */
    @Then("^the service returns a list with all the merchants$")
    public void theServiceReturnsAListWithAllTheMerchants() throws IOException {
        int status = con.getResponseCode();
        assertEquals(200, status);
    }

}

