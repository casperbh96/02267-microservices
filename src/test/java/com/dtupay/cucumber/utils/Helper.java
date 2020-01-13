package com.dtupay.cucumber.utils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.dtupay.app.*;
import com.dtupay.bank.BankAdapterJar;
import com.dtupay.bank.IBankAdapter;
import com.dtupay.database.*;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import cucumber.api.java.After;

public class Helper {
    public Set<String> usedBankAccounts = new HashSet<>();
    public String errorMessage;
    private IBankAdapter bank;
    private ICustomerAdapter customers;
    private IMerchantAdapter merchants;
    private ITokenAdapter tokens;
    private ITokenManagement tokenManager = new TokenManagement();
    public boolean errorHasOccured = false;

    public Helper() {
        this.bank = new BankAdapterJar();
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

    public Customer createDtuPayCustomerUsedToken(String name, String id, int tokens) {
        Customer customer = new Customer(id, name);
        customers.createCustomer(customer);
        tokenManager.CustomerGetTokens(customer, tokens);

        //TODO: this part should be taken care of by the token manager
        for (Token token : customer.getTokens()){
            this.tokens.createToken(token);
            token.setUsed(true);
        }

        return customer;
    }

    public Merchant createDtuPayMerchant(String name, String id) {
        Merchant merchant = new Merchant(id, name);
        merchants.createMerchant(merchant);
        return merchant;
    }

    public void createBankAccount(String name, String cpr, int initialBalance) throws Exception {
        bank.createAccount(name, cpr, BigDecimal.valueOf(initialBalance));
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
