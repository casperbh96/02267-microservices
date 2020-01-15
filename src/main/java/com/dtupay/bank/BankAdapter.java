package com.dtupay.bank;

import com.dtupay.bank.exceptions.BankAdapterException;
import dtu.ws.fastmoney.*;

import java.math.BigDecimal;
import java.util.List;

public class BankAdapter implements IBankAdapter {
    BankService bank = new BankServiceService().getBankServicePort();

    @Override
    public void createAccount(String name, String cpr, BigDecimal initialBalance) throws BankAdapterException {
        User u = new User();
        u.setFirstName(name);
        u.setLastName(name);
        u.setCprNumber(cpr);
        try {
            bank.createAccountWithBalance(u, initialBalance);
        } catch (BankServiceException_Exception e) {
            throw new BankAdapterException(e.getMessage());
        }
    }

    @Override
    public void removeAccountByCpr(String cpr) throws BankAdapterException {
        try {
            bank.retireAccount(cpr);
        } catch (BankServiceException_Exception e) {
            throw new BankAdapterException(e.getMessage());
        }
    }

    @Override
    public void makeTransaction(String customerCpr, String merchantCpr, BigDecimal amount, String comment) throws BankAdapterException {
        try {
            String customerBankId = bank.getAccountByCprNumber(customerCpr).getId();
            String merchantBankId = bank.getAccountByCprNumber(merchantCpr).getId();
            bank.transferMoneyFromTo(customerBankId, merchantBankId, amount, comment);
        } catch (BankServiceException_Exception e) {
            throw new BankAdapterException(e.getMessage());
        }
    }

    @Override
    public BigDecimal getBalanceByCPR(String cpr) throws BankAdapterException {
        try {
            return bank.getAccountByCprNumber(cpr).getBalance();
        } catch (BankServiceException_Exception e) {
            throw new BankAdapterException(e.getMessage());
        }
    }

    @Override
    public void deleteAllAccounts() throws BankAdapterException {
        List<AccountInfo> accounts = bank.getAccounts();
        for (AccountInfo acc : accounts) {
            try {
                bank.retireAccount(acc.getAccountId());
            } catch (BankServiceException_Exception e) {
                throw new BankAdapterException(e.getMessage());
            }
        }
    }


}
