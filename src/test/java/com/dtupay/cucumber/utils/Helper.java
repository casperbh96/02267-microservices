package com.dtupay.cucumber.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dtupay.app.*;
//import com.dtupay.bank.BankAdapterJar;
import com.dtupay.bank.BankAdapter;
import com.dtupay.bank.IBankAdapter;
import com.dtupay.database.*;
import com.dtupay.database.exceptions.CustomerIsUnableToReceiveNewTokens;
import com.dtupay.database.exceptions.TooManyTokenRequest;
import cucumber.api.java.After;

public class Helper {
    public Set<String> usedBankAccounts = new HashSet<String>();
    public String errorMessage;
    private IBankAdapter bank;
    private ICustomerAdapter customers;
    private IMerchantAdapter merchants;
    private ITokenAdapter tokens;
    private ITokenManagement tokenManager = new TokenManagement();
    public boolean errorHasOccured = false;

    public Helper() {
//        this.bank = new BankAdapterJar();
        this.bank = new BankAdapter();
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

    public Customer createDtuPayCustomer(int id, String name, String cpr, int tokens) throws CustomerIsUnableToReceiveNewTokens, TooManyTokenRequest {
        Customer customer = new Customer(id, cpr, name);
        customers.createCustomer(customer);
        tokenManager.CustomerGetTokens(customer, tokens, this.tokens);
        return customer;
    }

    public Customer createDtuPayCustomerUsedToken(int id, String name, String cpr, int tokens) throws CustomerIsUnableToReceiveNewTokens, TooManyTokenRequest {
        Customer customer = new Customer(id, cpr, name);
        customers.createCustomer(customer);
        tokenManager.CustomerGetTokens(customer, tokens, this.tokens);
        for (Token token : customer.getTokens()) {
            token.setUsed(true);
        }

        return customer;
    }


    public Customer createDtuPayCustomerInvalidToken(int id, String name, String cpr, int tokens) {
        Customer customer = new Customer(id, cpr, name);
        customers.createCustomer(customer);
        List<Token> tokenList = new ArrayList<>();
        for (int i = 0; i < tokens; i++) {
            tokenList.add(new Token());
        }
        customer.setTokens(tokenList);

        return customer;
    }

    public Merchant createDtuPayMerchant(int id, String name, String cpr) {
        Merchant merchant = new Merchant(id, cpr, name);
        merchants.createMerchant(merchant);
        return merchant;
    }

    public void createBankAccount(String name, String cpr, int initialBalance) throws Exception {
        bank.createAccount(name, cpr, BigDecimal.valueOf(initialBalance));
        usedBankAccounts.add(cpr);
    }

}
