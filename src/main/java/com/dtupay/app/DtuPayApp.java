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

    public DtuPayApp(Bank bank, ICustomerAdapter customers, IMerchantAdapter merchants) {
        this.bank = bank;
        this.customers = customers;
        this.merchants = merchants;
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
