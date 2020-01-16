package com.dtupay.bank;

import com.dtupay.bank.exceptions.BankAdapterException;

import java.math.BigDecimal;

public interface IBankAdapter {
    void createAccount(String name, int cpr, BigDecimal initialBalance) throws BankAdapterException;

    void removeAccountByCpr(int cpr) throws BankAdapterException;

    void makeTransaction(int customerCpr, int merchantCpr, BigDecimal amount, String comment) throws BankAdapterException;

    BigDecimal getBalanceByCPR(int cpr) throws BankAdapterException;

    void deleteAllCreatedAccounts() throws BankAdapterException;
}
