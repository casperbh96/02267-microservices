package com.dtupay.bank;

import com.dtupay.app.Customer;
import com.dtupay.app.Merchant;
import dtu.ws.fastmoney.BankServiceException;

import java.math.BigDecimal;

public interface IBankAdapter {
    void createAccount(String name, String cpr, BigDecimal initialBalance) throws Exception;

    void removeAccountByCpr(String cpr) throws Exception;

    void makeTransaction(String customerCpr, String merchantCpr, BigDecimal amount, String comment) throws Exception;

    BigDecimal getBalanceByCPR(String cpr) throws Exception;

    void deleteAllAccounts() throws Exception;
}
