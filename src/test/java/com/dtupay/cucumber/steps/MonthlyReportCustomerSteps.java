package com.dtupay.cucumber.steps;

import com.dtupay.app.DtuPayApp;
import com.dtupay.app.IDtuPayApp;
import com.dtupay.app.Transaction;
import com.dtupay.cucumber.utils.Helper;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.lexer.Tr;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthlyReportCustomerSteps {

    Helper helper;
    List<Integer> customerIds;
    List<Integer> merchantIds;
    Map<Integer, List<Transaction>> customerReports;

    public MonthlyReportCustomerSteps(Helper helper){
        this.helper = helper;
        this.customerIds = new ArrayList<>();
        this.merchantIds = new ArrayList<>();
        this.customerReports = new HashMap<>();
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
            Transaction t = helper.addTransaction(Timestamp.valueOf(d.get("timestamp")),
                    Integer.parseInt(d.get("fromId")), Integer.parseInt(d.get("toId")),
                    Integer.parseInt(d.get("tokenId")), new BigDecimal(d.get("amount")), Boolean.parseBoolean(d.get("isRefund")));
            t.setTimestamp(Timestamp.valueOf(d.get("timestamp")));
        });
    }

    @When("^DTU Pay generates monthly reports for (\\d+) (\\d+)$")
    public void dtu_Pay_generates_monthly_reports_for(int month, int year) throws Throwable {
        IDtuPayApp dtuPayApp = new DtuPayApp(helper.getBank(), helper.getCustomers(), helper.getMerchants(),
                helper.getTokens(), helper.getTransactionManager());
        for (int id : customerIds){
            customerReports.put(id, dtuPayApp.generateMonthlyCustomerReport(id, month, year));
        }
    }


    @Then("^customer (\\d+) will have (\\d+) transaction in his report$")
    public void customer_will_have_transaction_in_his_report(int id, int transactionCount) throws Throwable {
//        System.out.println(customerReports.get(id).size() + transactionCount);
        assert customerReports.get(id).size() == transactionCount;
    }

    @Then("^customer (\\d+) will have (\\d+) transactions in his report$")
    public void customer_will_have_transactions_in_his_report(int id, int transactionCount) throws Throwable {
//        System.out.println(customerReports.get(id).size() + transactionCount);
        assert customerReports.get(id).size() == transactionCount;
    }
}
