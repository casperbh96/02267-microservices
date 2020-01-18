package com.dtupay.app;

import com.dtupay.bank.exceptions.BankAdapterException;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IDtuPayApp {
    boolean checkTokenValidity(Token token) throws FakeToken, TokenAlreadyUsed;

    void transferMoney(String merchantId, Token merchantToken, BigDecimal amount, String description) throws BankAdapterException;
//    List<Map<String, List<Transaction>>> generateMonthlyCustomerReports();
}
