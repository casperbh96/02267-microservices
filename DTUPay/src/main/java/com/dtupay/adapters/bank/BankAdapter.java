package com.dtupay.adapters.bank;

import com.dtupay.adapters.bank.exceptions.BankAdapterException;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ionela
 * created a bank adapter class to handle the accounts of users
 */
public class BankAdapter implements IBankAdapter {
    private List<String> createdAccounts = new ArrayList<>();
    BankService bank = new BankServiceService().getBankServicePort();

    /**
     * function is used to create an account
     * @param name of user
     * @param cpr of user
     * @param initialBalance to be added
     * @throws BankAdapterException
     */
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

    /**
     * function to remove bank account
     * @param cpr used to remove account
     * @throws BankAdapterException
     */
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

    /**
     * function to make transactions by customer and merchants
     * @param customerCpr
     * @param merchantCvr
     * @param amount to be transferred
     * @param comment about the transaction
     * @throws BankAdapterException
     */
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

    /**
     * show balance by CPR number
     * @param cpr number
     * @return balance in the bank account
     * @throws BankAdapterException
     */
    @Override
    public BigDecimal getBalanceByCPR(String cpr) throws BankAdapterException {
        try {
            return bank.getAccountByCprNumber(cpr).getBalance();
        } catch (BankServiceException_Exception e) {
            throw new BankAdapterException(e.getMessage());
        }
    }

    /**
     * deletes all the accounts using id's
     * @throws BankAdapterException
     */
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
