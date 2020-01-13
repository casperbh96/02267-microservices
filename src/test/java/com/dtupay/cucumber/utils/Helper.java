package com.dtupay.cucumber.utils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.dtupay.app.*;
import com.dtupay.bank.IBankAdapter;
import com.dtupay.database.*;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import cucumber.api.java.After;
import dtu.ws.fastmoney.Bank;
import dtu.ws.fastmoney.User;

public class Helper {
    public Set<String> usedBankAccounts = new HashSet<>();
    public String errorMessage;
    private IBankAdapter bank;
    private ICustomerAdapter customers;
    private IMerchantAdapter merchants;
    private ITokenAdapter tokens;
    private ITokenManagement tokenManager = new TokenManagement();
    public boolean errorHasOccured = false;

    public Helper(BankFactory factory) {
        this.bank = factory.createBank();
        this.customers = new CustomerAdapter();
        this.merchants = new MerchantAdapter();
        this.tokens = new TokenAdapter();
    }

    public IBankAdapter getBank() {
        return bank;
    }

    public ICustomerAdapter getCustomers() {
        return customers;
    }

    public IMerchantAdapter getMerchants() {
        return merchants;
    }

    public ITokenAdapter getTokens() {
        return tokens;
    }

    public Customer createDtuPayCustomer(String name, String id, int tokens) {
        Customer customer = new Customer(id, name);
        customers.createCustomer(customer);
        tokenManager.CustomerGetTokens(customer, tokens);

        //TODO: this part should be taken care of by the token manager
        for (Token token : customer.getTokens()) {
            this.tokens.createToken(token);
        }
        return customer;
    }

    public Merchant createDtuPayMerchant(String name, String id, int tokens) {
        Merchant merchant = new Merchant(id, name);
        merchants.createMerchant(merchant);
        return merchant;
    }

    public void createBankAccount(String name, String cpr, int initialBalance) throws Exception {
        try {
            Customer c = customers.getCustomerByCustomerId(cpr);
            bank.createAccount(c, BigDecimal.valueOf(initialBalance));
        } catch (CustomerDoesNotExist e) {
            Merchant m = merchants.getMerchantByMerchantId(cpr);
            bank.createAccount(m, BigDecimal.valueOf(initialBalance));
        }
        usedBankAccounts.add(cpr);
    }

    @After
    public void retireUsedAccounts() {
        for (String cpr : usedBankAccounts) {
            try {
                bank.removeAccountByCpr(cpr);
            } catch (Exception e) {
                // ignore: account does not exist anymore
            }
        }
        usedBankAccounts.clear();
    }


}
