package com.dtupay.app;

import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.IMerchantAdapter;
import com.dtupay.database.ITokenAdapter;
import dtu.ws.fastmoney.Bank;
import dtu.ws.fastmoney.BankServiceException;

import java.math.BigDecimal;

public class DtuPayApp implements IDtuPayApp {

    private Bank bank;
    private ICustomerAdapter customers;
    private IMerchantAdapter merchants;
    private ITokenAdapter tokens;
    private ITokenManagement tokenManager;

    public DtuPayApp(Bank bank, ICustomerAdapter customers, IMerchantAdapter merchants, ITokenAdapter tokens) {
        this.bank = bank;
        this.customers = customers;
        this.merchants = merchants;
        this.tokens = tokens;
    }

    @Override
    public boolean checkTokenValidity(Token token){
        // TODO: also check the customerID who provided the token?
        if (tokens.checkExists(token) && !token.used)
            return true;
        return false;
    }

    @Override
    public void transferMoney(String accountIdFrom, String accountIdTo, BigDecimal amount, String description) {
        try {
            bank.transferMoneyFromTo(accountIdFrom, accountIdTo, amount, description);
        } catch (BankServiceException e) {
            e.printStackTrace();
        }
    }
}
