package com.dtupay.bank;

import dtu.ws.fastmoney.BankServiceException_Exception;

import java.math.BigDecimal;

public interface IBankAdapter {
    void createAccount(String name, String cpr, BigDecimal initialBalance) throws BankServiceException_Exception;

    void removeAccountByCpr(String cpr) throws BankServiceException_Exception;

    void makeTransaction(String customerCpr, String merchantCpr, BigDecimal amount, String comment) throws BankServiceException_Exception;

    BigDecimal getBalanceByCPR(String cpr) throws BankServiceException_Exception;

    void deleteAllAccounts() throws BankServiceException_Exception;
}
