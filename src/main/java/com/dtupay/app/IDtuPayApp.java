package com.dtupay.app;

import com.dtupay.bank.exceptions.BankAdapterException;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;

import java.math.BigDecimal;
import java.util.List;

public interface IDtuPayApp {
    boolean checkTokenValidity(Token token) throws FakeToken, TokenAlreadyUsed;

    void transferMoney(String merchantId, Token merchantToken, BigDecimal amount, String description) throws BankAdapterException;
    List<Transaction> generateMonthlyCustomerReport(int customerId, int month, int year);
}
