package com.customer.cucumber.steps;

import com.customer.manager.CustomerManager;
import com.customer.manager.ICustomerManager;
import com.customer.app.Customer;
import com.customer.database.exceptions.CustomerDoesNotExist;
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
public class BasicRestPointSteps {
    private URL addURL;
    HttpURLConnection connector;

    String customerCPR;
    String customerName;
    ICustomerManager c = new CustomerManager();
    Customer customer;

    /**
     * setting up customer for tests
     */
    @Before
    public void Setup() {
        customer = c.CreateCustomer("12345678", "Ismaa");
    }

    /**
     * checks for deletion of customer
     * @throws CustomerDoesNotExist
     */
    @After
    public void cleanUp() throws CustomerDoesNotExist {
        c.DeleteCustomerByCustomerId(customer.getId());
    }

    /**
     * given that customer has id
     * @param customerID
     * @throws MalformedURLException
     */
    @Given("^customer with ID (\\d+)$")
    public void customerWithID(int customerID) throws MalformedURLException {
        addURL = new URL("http://localhost:8081/customer/" + customerID);
    }

    /**
     * given that a new customer is created
     * @throws MalformedURLException
     */
    @Given("^a new customer we created$")
    public void aNewCustomerWeCreated() throws MalformedURLException {
        addURL = new URL("http://localhost:8081/customer/");
    }

    /**
     * checks if it returns the requested customer
     * @throws IOException
     */
    @When("^the customer is requested$")
    public void theCustomerIsRequested() throws IOException {
        SetConnectorAsGet();
    }

    /**
     * checks if we get an ok status from rest service
     * @param status
     * @throws IOException
     */
    @Then("^the rest service will send ok (\\d+) http status$")
    public void theRestServiceWillSendOkHttpStatus(int status) throws IOException {
        assertEquals(status, connector.getResponseCode());
    }

    /**
     * checks for an alternative where rest service can throw not found status
     * @param status
     * @throws IOException
     */
    @Then("^the rest service will send not found (\\d+) http status$")
    public void theRestServiceWillSendNotFoundHttpStatus(int status) throws IOException {
        assertEquals(status, connector.getResponseCode());
    }

    private void SetConnectorAsGet() throws IOException {
        connector = (HttpURLConnection) addURL.openConnection();
        connector.setRequestMethod("GET");
        connector.setConnectTimeout(10000);
    }

    /**
     * given a customer with cpr
     * @param cpr number
     * @param customer_name
     * @throws Throwable
     */
    @Given("^a new customer with CPR \"([^\"]*)\", name \"([^\"]*)\"$")
    public void aNewCustomerWithCPRName(String cpr, String customer_name) throws Throwable {
        addURL = new URL("http://localhost:8081/customer/");
        customerCPR = cpr;
        customerName = customer_name;
    }

    /**
     * checks if the POST request is working to update the customer
     * @throws IOException
     */
    @When("^the customer is posted to the service$")
    public void theCustomerIsPostedToTheService() throws IOException {
        final String POST_PARAMS = "{\n" + "\"name\": \""+customerName+"\",\r\n" +
                "    \"cpr\": \""+customerCPR+"\"" + "\n}";
        connector = (HttpURLConnection) addURL.openConnection();
        connector.setRequestMethod("POST");
        connector.setConnectTimeout(10000);
        connector.setRequestProperty("Content-Type", "application/json");
        connector.setDoOutput(true);
        OutputStream os = connector.getOutputStream();
        os.write(POST_PARAMS.getBytes());
    }

    /**
     * @returns true if POST is working fine
     * @param status
     * @throws IOException
     */
    @Then("^the rest service will post ok (\\d+) http status$")
    public void theRestServiceWillPostOkHttpStatus(int status) throws IOException {
        assertEquals(status, connector.getResponseCode());
    }

    /**
     * @returns the customer using rest service
     * @throws IOException
     */
    @Then("^the rest service returns the customer associated$")
    public void theRestServiceReturnsTheCustomerAssociated() throws IOException {
        int status = connector.getResponseCode();
        assertEquals(200, status);

    }

    /**
     * checks the connection for delete request
     * @throws IOException
     */
    @When("^the customer is requested to be deleted$")
    public void theCustomerIsRequestedToBeDeleted() throws IOException {
        connector = (HttpURLConnection) addURL.openConnection();
        connector.setRequestMethod("DELETE");
        connector.setConnectTimeout(10000);
    }

    /**
     * asserts true if deletion operation is working fine
     * @throws IOException
     */
    @Then("^the rest service deletes customer correctly$")
    public void theRestServiceDeletesCustomerCorrectly() throws IOException {
        int status = connector.getResponseCode();
        assertEquals(200, status);
    }

    /**
     * asserts true if update operation is working fine using POST request
     * @throws IOException
     */
    @Then("^the rest service posts customer correctly$")
    public void theRestServicePostsCustomerCorrectly() throws IOException {
        int status = connector.getResponseCode();
        assertEquals(200, status);

    }

    /**
     * asserts true if update operation is working
     * @throws IOException
     */
    @Then("^the rest service updates customer correctly$")
    public void theRestServiceUpdatesCustomerCorrectly() throws IOException {
        int status = connector.getResponseCode();
        assertEquals(200, status);
    }

    /**
     * checks connection for customer update using CPR
     * @param cpr
     * @param customer_name
     * @throws Throwable
     */
    @When("^the customer is updated with CPR \"([^\"]*)\", name \"([^\"]*)\"$")
    public void theCustomerIsUpdatedWithCPRName(String cpr, String customer_name) throws Throwable {
        final String PUT_PARAMS = "{\n" + "\"name\": \""+customer_name+"\",\r\n" +
                "    \"id\": "+customer.getId()+",\r\n" +
                "    \"cpr\": \""+cpr+"\"" + "\n}";
        connector = (HttpURLConnection) addURL.openConnection();
        connector.setRequestMethod("PUT");
        connector.setConnectTimeout(10000);
        connector.setRequestProperty("Content-Type", "application/json");
        connector.setDoOutput(true);
        OutputStream os = connector.getOutputStream();
        os.write(PUT_PARAMS.getBytes());
    }

    /**
     * checks given condition for customer that is created
     * @throws MalformedURLException
     */
    @Given("^the customer we created$")
    public void theCustomerWeCreated() throws MalformedURLException {
        addURL = new URL("http://localhost:8081/customer/" + customer.getId());
    }

    /**
     * checks the given condition for having customers in the database
     * @throws MalformedURLException
     */
    @Given("^we have some customers in the database$")
    public void weHaveSomeCustomersInTheDatabase() throws MalformedURLException {
        addURL = new URL("http://localhost:8081/customer/");
    }

    /**
     * checks for when we request to see all the customers
     * @throws IOException
     */
    @When("^we request to see all the customers$")
    public void weRequestToSeeAllTheCustomers() throws IOException {
        connector = (HttpURLConnection) addURL.openConnection();
        connector.setRequestMethod("GET");
        connector.setConnectTimeout(10000);
    }

    /**
     * checks for assertion condition if rest service is returning all the customers or not
     * @throws IOException
     */
    @Then("^the service returns a list with all the customers$")
    public void theServiceReturnsAListWithAllTheCustomers() throws IOException {
        int status = connector.getResponseCode();
        assertEquals(200, status);
    }
}
