package com.dtupay.adapters.bank;

import com.dtupay.adapters.bank.exceptions.BankAdapterException;

import java.math.BigDecimal;

/**
 * @author Dumitru
 * represents the interface which to be implemented by back adapter class
 */

public interface IBankAdapter {
    void createAccount(String name, String cpr, BigDecimal initialBalance) throws BankAdapterException;

    void removeAccountByCpr(String cpr) throws BankAdapterException;

    void makeTransaction(String customerCpr, String merchantCvr, BigDecimal amount, String comment) throws BankAdapterException;

    BigDecimal getBalanceByCPR(String cpr) throws BankAdapterException;

    void deleteAllCreatedAccounts() throws BankAdapterException;
}
