package com.dtupay.cucumber.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dtupay.app.*;
import com.dtupay.bank.BankAdapter;
import com.dtupay.bank.IBankAdapter;
import com.dtupay.database.*;
import com.dtupay.database.exceptions.CustomerIsUnableToReceiveNewTokens;
import com.dtupay.database.exceptions.TooManyTokenRequest;

public class Helper {
    public String errorMessage;
    private IBankAdapter bank;
    private ICustomerAdapter customers;
    private IMerchantAdapter merchants;
    private ITokenAdapter tokens;
    private ITokenManagement tokenManager = new TokenManagement();
    private ITransactionManager transactionManager;
    public boolean errorHasOccured = false;

    public Helper() {
        this.bank = new BankAdapter();
        this.customers = new CustomerAdapter();
        this.merchants = new MerchantAdapter();
        this.tokens = new TokenAdapter();

        transactionManager = new TransactionManager(new TransactionAdapter());
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

    public ITransactionManager getTransactionManager() {
        return transactionManager;
    }

    public Customer createDtuPayCustomer(String name, String cpr, int tokens) throws CustomerIsUnableToReceiveNewTokens, TooManyTokenRequest {
        Customer customer = customers.createCustomer(cpr, name);
        tokenManager.CustomerGetTokens(customer, tokens, this.tokens);
        return customer;
    }

    public Customer createDtuPayCustomerUsedToken(String name, String cpr, int tokens) throws CustomerIsUnableToReceiveNewTokens, TooManyTokenRequest {
        Customer customer = customers.createCustomer(cpr, name);
        tokenManager.CustomerGetTokens(customer, tokens, this.tokens);
        for (Token token : customer.getTokens()) {
            token.setUsed(true);
        }

        return customer;
    }


    public Customer createDtuPayCustomerInvalidToken(String name, String cpr, int tokens) {
        Customer customer = customers.createCustomer(cpr, name);
        List<Token> tokenList = new ArrayList<>();
        for (int i = 0; i < tokens; i++) {
            tokenList.add(new Token());
        }
        customer.setTokens(tokenList);

        return customer;
    }

    public Merchant createDtuPayMerchant(String name, String cpr) {
        Merchant merchant = merchants.createMerchant(cpr, name);
        return merchant;
    }

    public void createBankAccount(String name, String cpr, int initialBalance) throws Exception {
        bank.createAccount(name, cpr, BigDecimal.valueOf(initialBalance));
    }


    public Transaction addTransaction(Timestamp timestamp, int fromId, int toId, String tokenId, BigDecimal amount, boolean isRefund){
        return transactionManager.registerTransaction(timestamp, fromId, toId, tokenId, amount, isRefund);
    }

}
