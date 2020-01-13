package com.dtupay.bank;

import com.dtupay.app.Customer;
import com.dtupay.app.Merchant;
import dtu.ws.fastmoney.BankServiceException;

import java.math.BigDecimal;

public interface IBankAdapter {
    void createAccount(Customer customer, BigDecimal initialBalance) throws BankServiceException;

    void createAccount(Merchant merchant, BigDecimal initialBalance) throws BankServiceException;

    void makeTransaction(Customer from, Merchant to, BigDecimal amount, String comment) throws BankServiceException;
}
