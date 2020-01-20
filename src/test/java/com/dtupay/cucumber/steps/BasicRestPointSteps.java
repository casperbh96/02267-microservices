package com.dtupay.cucumber.steps;

import com.dtupay.BusinessLogic.BusinessLogicForCustomer;
import com.dtupay.BusinessLogic.IBusinessLogicForCustomer;
import com.dtupay.app.Customer;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class BasicRestPointSteps {
    private URL addURL;
    HttpURLConnection connector;

    String customerCPR;
    String customerName;
    IBusinessLogicForCustomer c = new BusinessLogicForCustomer();
    Customer customer;

    @Before
    public void Setup() {
        customer = c.createCustomer("12345678", "Ismaa");
    }

    @After
    public void cleanUp() throws CustomerDoesNotExist {
        c.deleteCustomerByCustomerId(customer.getId());
    }

    @Given("^customer with ID (\\d+)$")
    public void customerWithID(int customerID) throws MalformedURLException {
        addURL = new URL("http://localhost:8080/customer/" + customerID);
    }

    @Given("^a new customer we created$")
    public void aNewCustomerWeCreated() throws MalformedURLException {
        addURL = new URL("http://localhost:8080/customer/");
    }


    @When("^the customer is requested$")
    public void theCustomerIsRequested() throws IOException {
        SetConnectorAsGet();
    }

    @Then("^the rest service will send ok (\\d+) http status$")
    public void theRestServiceWillSendOkHttpStatus(int status) throws IOException {
        assertEquals(status, connector.getResponseCode());
    }

    @Then("^the rest service will send not found (\\d+) http status$")
    public void theRestServiceWillSendNotFoundHttpStatus(int status) throws IOException {
        assertEquals(status, connector.getResponseCode());
    }

    private void SetConnectorAsGet() throws IOException {
        connector = (HttpURLConnection) addURL.openConnection();
        connector.setRequestMethod("GET");
    }

    @Given("^a new customer with CPR \"([^\"]*)\", name \"([^\"]*)\"$")
    public void aNewCustomerWithCPRName(String cpr, String customer_name) throws Throwable {
        addURL = new URL("http://localhost:8080/customer/");
        customerCPR = cpr;
        customerName = customer_name;
    }

    @When("^the customer is posted to the service$")
    public void theCustomerIsPostedToTheService() throws IOException {
        final String POST_PARAMS = "{\n" + "\"name\": \""+customerName+"\",\r\n" +
                "    \"cpr\": \""+customerCPR+"\"" + "\n}";
        connector = (HttpURLConnection) addURL.openConnection();
        connector.setRequestMethod("POST");
        connector.setRequestProperty("Content-Type", "application/json");
        connector.setDoOutput(true);
        OutputStream os = connector.getOutputStream();
        os.write(POST_PARAMS.getBytes());
    }
    @Then("^the rest service will post ok (\\d+) http status$")
    public void theRestServiceWillPostOkHttpStatus(int status) throws IOException {
        assertEquals(status, connector.getResponseCode());
    }

    @Then("^the rest service returns the customer associated$")
    public void theRestServiceReturnsTheCustomerAssociated() throws IOException {
        int status = connector.getResponseCode();
        assertEquals(200, status);

    }

    @When("^the customer is requested to be deleted$")
    public void theCustomerIsRequestedToBeDeleted() throws IOException {
        connector = (HttpURLConnection) addURL.openConnection();
        connector.setRequestMethod("DELETE");
    }

    @Then("^the rest service deletes customer correctly$")
    public void theRestServiceDeletesCustomerCorrectly() throws IOException {
        int status = connector.getResponseCode();
        assertEquals(200, status);
    }

    @Then("^the rest service posts customer correctly$")
    public void theRestServicePostsCustomerCorrectly() throws IOException {
        int status = connector.getResponseCode();
        assertEquals(200, status);

    }

    @Then("^the rest service updates customer correctly$")
    public void theRestServiceUpdatesCustomerCorrectly() throws IOException {
        int status = connector.getResponseCode();
        assertEquals(200, status);
    }

    @When("^the customer is updated with CPR \"([^\"]*)\", name \"([^\"]*)\"$")
    public void theCustomerIsUpdatedWithCPRName(String cpr, String customer_name) throws Throwable {
        final String PUT_PARAMS = "{\n" + "\"name\": \""+customer_name+"\",\r\n" +
                "    \"id\": "+customer.getId()+",\r\n" +
                "    \"cpr\": \""+cpr+"\"" + "\n}";
        connector = (HttpURLConnection) addURL.openConnection();
        connector.setRequestMethod("PUT");
        connector.setRequestProperty("Content-Type", "application/json");
        connector.setDoOutput(true);
        OutputStream os = connector.getOutputStream();
        os.write(PUT_PARAMS.getBytes());
    }

    @Given("^the customer we created$")
    public void theCustomerWeCreated() throws MalformedURLException {
        addURL = new URL("http://localhost:8080/merchant/" + customer.getId());
    }

    @Given("^we have some customers in the database$")
    public void weHaveSomeCustomersInTheDatabase() throws MalformedURLException {
        addURL = new URL("http://localhost:8080/merchant/");
    }

    @When("^we request to see all the customers$")
    public void weRequestToSeeAllTheCustomers() throws IOException {
        connector = (HttpURLConnection) addURL.openConnection();
        connector.setRequestMethod("GET");
    }

    @Then("^the service returns a list with all the customers$")
    public void theServiceReturnsAListWithAllTheCustomers() throws IOException {
        int status = connector.getResponseCode();
        assertEquals(200, status);
    }
}
