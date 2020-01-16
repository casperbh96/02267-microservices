package com.dtupay.cucumber.steps;

import com.dtupay.app.Transaction;
import com.dtupay.cucumber.utils.Helper;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MonthlyReportCustomerSteps {

    Helper helper;
    List<String> customerIds;
    List<String> merchantIds;

    public MonthlyReportCustomerSteps(Helper helper){
        this.helper = helper;
        this.customerIds = new ArrayList<>();
        this.merchantIds = new ArrayList<>();
    }

    @Given("^the following customers:$")
    public void the_following_customers(DataTable arg1) throws Throwable {
        List<Map<String, String>> userDetails = arg1.asMaps(String.class, String.class);
        for(Map<String, String> details : userDetails){
            customerIds.add(helper.createDtuPayCustomer(details.get("name"), details.get("id"), 0).getId());
        }
    }

    @Given("^the following merchants:$")
    public void the_following_merchants(DataTable arg1) throws Throwable {
        List<Map<String, String>> userDetails = arg1.asMaps(String.class, String.class);
        for(Map<String, String> details : userDetails){
            merchantIds.add(helper.createDtuPayMerchant(details.get("name"), details.get("id")).getId());
        }
    }

    @Given("^the following transactions:$")
    public void the_following_transactions(DataTable arg1) throws Throwable {
        List<Map<String, String>> transDetails = arg1.asMaps(String.class, String.class);
        transDetails.forEach(d -> {
            Transaction t = helper.addTransaction(d.get("customer"), d.get("merchant"), d.get("tokenId"), new BigDecimal(d.get("amount")));
            t.setTimestamp(Timestamp.valueOf(d.get("timestamp")));
        });
    }

    @When("^DTU Pay sends out the monthly reports for \"([^\"]*)\" (\\d+)$")
    public void dtu_Pay_sends_out_the_monthly_reports_for(String arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^customer \"([^\"]*)\" will have (\\d+) transaction in his report$")
    public void customer_will_have_transaction_in_his_report(String arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^customer \"([^\"]*)\" will have (\\d+) transactions in his report$")
    public void customer_will_have_transactions_in_his_report(String arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
