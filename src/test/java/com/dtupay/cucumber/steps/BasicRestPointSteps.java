package com.dtupay.cucumber.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BasicRestPointSteps {
    private URL addURL;
    HttpURLConnection connector;

    String customerCPR;
    String customerName;

    @Given("^customer with (\\d+) as Id$")
    public void customerWithAsId(int id) throws MalformedURLException {
        addURL = new URL("http://localhost:8080/customer/" + id);
    }

    @When("^the customer is requested$")
    public void theCustomerIsRequested() throws IOException {
        SetConnectorAsGet();
    }

    @Then("^the rest service will send ok (\\d+) http status$")
    public void theRestServiceWillSendOkHttpStatus(int status) throws IOException {
        Assert.assertEquals(status, connector.getResponseCode());
    }

    @Given("^customer with (\\d+) as id$")
    public void customerWithAWrongId(int id) throws Throwable {
        addURL = new URL("http://localhost:8080/customer/" + id);
    }

    @When("^the customer is requestedTest$")
    public void theCustomerIsRequestedWithABadRequest() throws IOException {
        SetConnectorAsGet();
    }

    @Then("^the rest service will send not found (\\d+) http status$")
    public void theRestServiceWillSendNotFoundHttpStatus(int status) throws IOException {
        Assert.assertEquals(status, connector.getResponseCode());
    }

    private void SetConnectorAsGet() throws IOException {
        connector = (HttpURLConnection) addURL.openConnection();
        connector.setRequestMethod("GET");
    }

    @Given("^a customer named \"([^\"]*)\" and cpr \"([^\"]*)\"$")
    public void aCustomerNamedAndCpr(String name, String cpr) throws Throwable {
        addURL = new URL("http://localhost:8080/customer/");
        customerCPR = cpr;
        customerName = name;
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
        Assert.assertEquals(status, connector.getResponseCode());
    }
}
