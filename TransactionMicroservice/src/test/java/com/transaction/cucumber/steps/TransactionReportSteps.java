package com.transaction.cucumber.steps;

import com.transaction.app.*;
import com.transaction.database.TransactionAdapter;
import com.transaction.manager.ITransactionManager;
import com.transaction.manager.TransactionManager;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

public class TransactionReportSteps {

    Set<Integer> customerIds;
    Set<Integer> merchantIds;
    Map<Integer, List<TransactionCustomer>> customerReports;
    Map<Integer, List<TransactionMerchant>> merchantReports;
    List<Transaction> transactions;
    ITransactionManager transactionManager;

    public TransactionReportSteps(){
        this.customerIds = new HashSet<>();
        this.merchantIds = new HashSet<>();
        this.customerReports = new HashMap<>();
        this.merchantReports = new HashMap<>();
        this.transactions = new ArrayList<>();

        transactionManager = new TransactionManager(new TransactionAdapter());
    }

    @Given("^the following transactions:$")
    public void the_following_transactions(DataTable arg1) throws Throwable {
        List<Map<String, String>> transDetails = arg1.asMaps(String.class, String.class);
        transDetails.forEach(d -> {
            boolean isRefund = Boolean.parseBoolean(d.get("isRefund"));
            if (!isRefund) {
                customerIds.add(Integer.parseInt(d.get("from")));
            } else {
                merchantIds.add(Integer.parseInt(d.get("from")));
            }
            transactions.add(transactionManager.registerTransaction(
                    Timestamp.valueOf(d.get("timestamp")),
                    Integer.parseInt(d.get("from")),
                    Integer.parseInt(d.get("to")),
                    Integer.parseInt(d.get("tokenId")),
                    new BigDecimal(d.get("amount")),
                    isRefund));
        });
    }

    @When("^transaction manager generates monthly customer reports for (\\d+) (\\d+)$")
    public void transaction_manager_generates_monthly_customer_reports_for(int arg1, int arg2) throws Throwable {
        for (int id : customerIds){
            customerReports.put(id, transactionManager.getCustomerMonthlyReport(id, arg1, arg2));
        }
    }

    @When("^transaction manager generates monthly merchant reports for (\\d+) (\\d+)$")
    public void transaction_manager_generates_monthly_merchant_reports_for(int arg1, int arg2) throws Throwable {
        for (int id : merchantIds){
            merchantReports.put(id, transactionManager.getMerchantMonthlyReport(id, arg1, arg2));
        }
    }

    @Then("^the following customer reports will be generated:$")
    public void the_following_customer_reports_will_be_generated(DataTable arg1) throws Throwable {
        List<Map<String, String>> reportDetails = arg1.asMaps(String.class, String.class);
        reportDetails.forEach(d -> {
            int id = Integer.parseInt(d.get("customerId"));
            Assert.assertEquals(Integer.parseInt(d.get("transactionsInReport")), customerReports.get(id).size());
        });
    }

    @Then("^the following merchant reports will be generated:$")
    public void the_following_merchant_reports_will_be_generated(DataTable arg1) throws Throwable {
        List<Map<String, String>> reportDetails = arg1.asMaps(String.class, String.class);
        reportDetails.forEach(d -> {
            int id = Integer.parseInt(d.get("merchantId"));
            Assert.assertEquals(Integer.parseInt(d.get("transactionsInReport")), merchantReports.get(id).size());
        });
    }

}
