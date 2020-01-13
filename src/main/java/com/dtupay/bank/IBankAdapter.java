package com.dtupay.bank;

import com.dtupay.app.Customer;
import com.dtupay.app.Merchant;
import dtu.ws.fastmoney.BankServiceException;

import java.math.BigDecimal;

public interface IBankAdapter {
    void createAccount(String name, String cpr, BigDecimal initialBalance) throws BankServiceException;

    void removeAccountByCpr(String cpr) throws BankServiceException;

    void makeTransaction(String customerCpr, String merchantCpr, BigDecimal amount, String comment) throws BankServiceException;

    BigDecimal getBalanceByCPR(String cpr) throws BankServiceException;
}
