package com.dtupay.bank;

import com.dtupay.app.Customer;
import com.dtupay.app.Merchant;
import dtu.ws.fastmoney.BankServiceException;

public interface IBankAdapter {
    void createAccount(Customer customer, int initialBalance);

    void createAccount(Merchant merchant, int initialBalance);

    void makeTransaction(Customer from, Merchant to, int amount, String comment) throws BankServiceException;
}
