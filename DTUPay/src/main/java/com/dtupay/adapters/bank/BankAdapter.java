package com.dtupay.adapters.bank;

import com.dtupay.adapters.bank.exceptions.BankAdapterException;
import dtu.ws.fastmoney.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankAdapter implements IBankAdapter {
    private List<String> createdAccounts = new ArrayList<>();
    BankService bank = new BankServiceService().getBankServicePort();

    @Override
    public void createAccount(String name, String cpr, BigDecimal initialBalance) throws BankAdapterException {
        User u = new User();
        u.setFirstName(name);
        u.setLastName(name);
        u.setCprNumber(cpr);
        try {
            String accountId = bank.createAccountWithBalance(u, initialBalance);
            createdAccounts.add(accountId);
        } catch (BankServiceException_Exception e) {
            throw new BankAdapterException(e.getMessage());
        }
    }

    @Override
    public void removeAccountByCpr(String cpr) throws BankAdapterException {
        try {
            String id = bank.getAccountByCprNumber(cpr).getId();
            createdAccounts.remove(id);
            bank.retireAccount(id);
        } catch (BankServiceException_Exception e) {
            throw new BankAdapterException(e.getMessage());
        }
    }

    @Override
    public void makeTransaction(String customerCpr, String merchantCvr, BigDecimal amount, String comment) throws BankAdapterException {
        try {
            String customerBankId = bank.getAccountByCprNumber(customerCpr).getId();
            String merchantBankId = bank.getAccountByCprNumber(merchantCvr).getId();
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
    public void deleteAllCreatedAccounts() throws BankAdapterException {
        for (String id : createdAccounts) {
            try {
                bank.retireAccount(id);
            } catch (BankServiceException_Exception e) {
                throw new BankAdapterException(e.getMessage());
            }
        }
        createdAccounts.clear();
    }

}
