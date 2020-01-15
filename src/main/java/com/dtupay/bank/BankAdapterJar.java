package com.dtupay.bank;

import dtu.ws.fastmoney.*;
import dtu.ws.fastmoney.persistency.*;

import java.math.BigDecimal;

public class BankAdapterJar implements IBankAdapter {
    Bank bank = new Bank();
    Repository repo = new InMemoryRepository();

    public BankAdapterJar() {
        bank.setRepository(repo);
    }

    @Override
    public void createAccount(String name, String cpr, BigDecimal initialBalance) throws BankServiceException {
        User u = new User();
        u.setFirstName(name);
        u.setLastName(name);
        u.setCprNumber(cpr);

        bank.createAccountWithBalance(u, initialBalance);
    }

    @Override
    public void removeAccountByCpr(String cpr) throws BankServiceException {
        bank.retireAccount(cpr);
    }

    @Override
    public void makeTransaction(String customerCpr, String merchantCpr, BigDecimal amount, String comment) throws BankServiceException {
        String customerBankId = repo.readAccountByCpr(customerCpr).getId();
        String merchantBankId = repo.readAccountByCpr(merchantCpr).getId();
        bank.transferMoneyFromTo(customerBankId, merchantBankId, amount, comment);
    }

    @Override
    public BigDecimal getBalanceByCPR(String cpr) throws BankServiceException {
        return bank.getAccountByCprNumber(cpr).getBalance();
    }

//    public static void main(String[] args) throws Exception {
//        BankAdapterJar b = new BankAdapterJar();
//
//        b.createAccount("Dmitry", "1", BigDecimal.valueOf(1000));
//        b.createAccount("Bilka", "2", BigDecimal.valueOf(100000));
//
//        for (AccountInfo acc : b.bank.getAccounts()) {
//            System.out.print(acc.getUser().getFirstName() + " ");
//            System.out.println(b.bank.getAccount(acc.getAccountId()).getBalance());
//        }
//
//        b.makeTransaction("1", "2", BigDecimal.valueOf(500), "pizza");
//
//        for (AccountInfo acc : b.bank.getAccounts()) {
//            System.out.print(acc.getUser().getFirstName() + " ");
//            System.out.println(b.bank.getAccount(acc.getAccountId()).getBalance());
//        }
//    }

}

