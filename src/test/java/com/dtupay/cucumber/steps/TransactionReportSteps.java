package com.dtupay.cucumber.steps;

import com.dtupay.app.*;
import com.dtupay.cucumber.utils.Helper;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionReportSteps {

    Helper helper;
    Map<String, Integer> customerCprToIds;
    Map<String, Integer> merchantCprToIds;
    Map<Integer, List<TransactionCustomer>> customerReports;
    Map<Integer, List<TransactionMerchant>> merchantReports;
    List<Transaction> transactions;

    public TransactionReportSteps(Helper helper){
        this.helper = helper;
        this.customerCprToIds = new HashMap<>();
        this.merchantCprToIds = new HashMap<>();
        this.customerReports = new HashMap<>();
        this.merchantReports = new HashMap<>();
        this.transactions = new ArrayList<>();
    }

    @Given("^the following customers:$")
    public void the_following_customers(DataTable arg1) throws Throwable {
        List<Map<String, String>> userDetails = arg1.asMaps(String.class, String.class);
        for(Map<String, String> details : userDetails){
            customerCprToIds.put(details.get("cpr"),
                    helper.createDtuPayCustomer(details.get("name"), details.get("cpr"), 0).getId());
        }
    }

    @Given("^the following merchants:$")
    public void the_following_merchants(DataTable arg1) throws Throwable {
        List<Map<String, String>> userDetails = arg1.asMaps(String.class, String.class);
        for(Map<String, String> details : userDetails){
            merchantCprToIds.put(details.get("cvr"),
                    helper.createDtuPayMerchant(details.get("name"), details.get("cvr")).getId());
        }
    }

    @Given("^the following transactions:$")
    public void the_following_transactions(DataTable arg1) throws Throwable {
        List<Map<String, String>> transDetails = arg1.asMaps(String.class, String.class);
        transDetails.forEach(d -> {
            boolean isRefund = Boolean.parseBoolean(d.get("isRefund"));
            transactions.add(helper.addTransaction(
                    Timestamp.valueOf(d.get("timestamp")),
                    !isRefund ? customerCprToIds.get(d.get("from")) : merchantCprToIds.get(d.get("from")),
                    !isRefund ? merchantCprToIds.get(d.get("to")) : customerCprToIds.get(d.get("to")),
                    Integer.parseInt(d.get("tokenId")),
                    new BigDecimal(d.get("amount")),
                    isRefund));
        });
    }

    @When("^DTU Pay generates monthly customer reports for (\\d+) (\\d+)$")
    public void dtu_Pay_generates_monthly_customer_reports_for(int month, int year) throws Throwable {
        IDtuPayApp dtuPayApp = new DtuPayApp(helper.getBank(), helper.getCustomers(), helper.getMerchants(),
                helper.getTokens(), helper.getTransactionManager());
        for (int id : customerCprToIds.values()){
            customerReports.put(id, dtuPayApp.generateMonthlyCustomerReport(id, month, year));
        }
    }

    @Then("^the following customer reports will be generated:$")
    public void the_following_customer_reports_will_be_generated(DataTable arg1) throws Throwable {
        List<Map<String, String>> reportDetails = arg1.asMaps(String.class, String.class);
        reportDetails.forEach(d -> {
            int id = customerCprToIds.get(d.get("customerCpr"));
            Assert.assertEquals(Integer.parseInt(d.get("transactionsInReport")), customerReports.get(id).size());
        });
    }

    @When("^DTU Pay generates monthly merchant reports for (\\d+) (\\d+)$")
    public void dtu_Pay_generates_monthly_merchant_reports_for(int month, int year) throws Throwable {
        IDtuPayApp dtuPayApp = new DtuPayApp(helper.getBank(), helper.getCustomers(), helper.getMerchants(),
                helper.getTokens(), helper.getTransactionManager());
        for (int id : merchantCprToIds.values()){
            merchantReports.put(id, dtuPayApp.generateMonthlyMerchantReport(id, month, year));
        }
    }

    @Then("^the following merchant reports will be generated:$")
    public void the_following_merchant_reports_will_be_generated(DataTable arg1) throws Throwable {
        List<Map<String, String>> reportDetails = arg1.asMaps(String.class, String.class);
        reportDetails.forEach(d -> {
            int id = merchantCprToIds.get(d.get("merchantCvr"));
            Assert.assertEquals(Integer.parseInt(d.get("transactionsInReport")), merchantReports.get(id).size());
        });
    }

}
