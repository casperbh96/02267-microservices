package com.dtupay.app;

import com.dtupay.bank.exceptions.BankAdapterException;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.IMerchantAdapter;
import com.dtupay.database.ITokenAdapter;
import com.dtupay.bank.IBankAdapter;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;
import com.dtupay.database.TransactionAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DtuPayApp implements IDtuPayApp {

    private IBankAdapter bank;
    private ICustomerAdapter customers;
    private IMerchantAdapter merchants;
    private ITokenAdapter tokens;
    private ITokenManagement tokenManager;
    private ITransactionManager transactionManager;

    public DtuPayApp(IBankAdapter bank, ICustomerAdapter customers, IMerchantAdapter merchants, ITokenAdapter tokens, ITransactionManager transactionManager) {
        this.bank = bank;
        this.customers = customers;
        this.merchants = merchants;
        this.tokens = tokens;

        this.transactionManager = transactionManager;
    }

    public void setTransactionManager(ITransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public boolean checkTokenValidity(Token token) throws FakeToken, TokenAlreadyUsed {
        // TODO: also check the customerID who provided the token?
        return tokens.isTokenValid(token.getId());
    }

    @Override
    public void transferMoney(String merchantId, Token customerToken, BigDecimal amount, String description) throws BankAdapterException {
        // assumption that token is valid at this point..
        try {
            Customer customer = customers.getCustomerByCustomerId(customerToken.customerId);
            bank.makeTransaction(customer.getCpr(), merchantId, amount, description);
        } catch (CustomerDoesNotExist customerDoesNotExist) {
            customerDoesNotExist.printStackTrace();
        }
    }

    public List<Transaction> generateMonthlyCustomerReport(int customerId, int month, int year) throws CustomerDoesNotExist {
        return transactionManager.getCustomerMonthlyReport(customerId, month, year);
    }

    @Override
    public List<Transaction> generateMonthlyMerchantReport(int merchantId, int month, int year) {
        return transactionManager.getMerchantMonthlyReport(merchantId, month, year);
    }

}
