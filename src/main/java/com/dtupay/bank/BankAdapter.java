package com.dtupay.bank;

import dtu.ws.fastmoney.*;

import java.math.BigDecimal;
import java.util.List;

public class BankAdapter implements IBankAdapter {
    BankService bank = new BankServiceService().getBankServicePort();

    @Override
    public void createAccount(String name, String cpr, BigDecimal initialBalance) throws BankServiceException_Exception {
        User u = new User();
        u.setFirstName(name);
        u.setLastName(name);
        u.setCprNumber(cpr);
        bank.createAccountWithBalance(u, initialBalance);
    }

    @Override
    public void removeAccountByCpr(String cpr) throws BankServiceException_Exception {
        bank.retireAccount(cpr);
    }

    @Override
    public void makeTransaction(String customerCpr, String merchantCpr, BigDecimal amount, String comment) throws BankServiceException_Exception {
        String customerBankId = bank.getAccountByCprNumber(customerCpr).getId();
        String merchantBankId = bank.getAccountByCprNumber(merchantCpr).getId();
        bank.transferMoneyFromTo(customerBankId, merchantBankId, amount, comment);
    }

    @Override
    public BigDecimal getBalanceByCPR(String cpr) throws BankServiceException_Exception {
        return bank.getAccountByCprNumber(cpr).getBalance();
    }

    @Override
    public void deleteAllAccounts() throws BankServiceException_Exception {
        List<AccountInfo> accounts = bank.getAccounts();
        for (AccountInfo acc : accounts){
            bank.retireAccount(acc.getAccountId());
        }
    }


}
