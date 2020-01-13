package com.dtupay.bank;

import com.dtupay.app.Customer;
import com.dtupay.app.Merchant;
import dtu.ws.fastmoney.*;
import dtu.ws.fastmoney.persistency.*;

import java.math.BigDecimal;

public class BankAdapterJar implements IBankAdapter {
    Bank bank = new Bank();
    Repository repo = new InMemoryRepository();

    public BankAdapterJar() {
        bank.setRepository(repo);
    }

    private void addAccount(String name, String cpr, BigDecimal initialBalance) throws BankServiceException {
        User u = new User();
        u.setFirstName(name);
        u.setLastName(name);
        u.setCprNumber(cpr);

        bank.createAccountWithBalance(u, initialBalance);
    }

    @Override
    public void createAccount(Customer customer, BigDecimal initialBalance) throws BankServiceException {
        this.addAccount(customer.getName(), customer.getId(), initialBalance);
    }

    @Override
    public void createAccount(Merchant merchant, BigDecimal initialBalance) throws BankServiceException {
        this.addAccount(merchant.getName(), merchant.getId(), initialBalance);
    }

    @Override
    public void makeTransaction(Customer from, Merchant to, BigDecimal amount, String comment) throws BankServiceException {
        String customerBankId = repo.readAccountByCpr(from.getId()).getId();
        String merchantBankId = repo.readAccountByCpr(to.getId()).getId();
        bank.transferMoneyFromTo(customerBankId, merchantBankId, amount, comment);
    }

    @Override
    public void makeTransaction(String customerCpr, String merchantCpr, BigDecimal amount, String comment) throws BankServiceException {
        String customerBankId = repo.readAccountByCpr(customerCpr).getId();
        String merchantBankId = repo.readAccountByCpr(merchantCpr).getId();
        bank.transferMoneyFromTo(customerBankId, merchantBankId, amount, comment);
    }

//    public static void main(String[] args) throws Exception {
//        BankAdapterJar b = new BankAdapterJar();
//
//        Customer dmr = new Customer("1111111111", "Dmitry");
//        Merchant bilka = new Merchant("12", "Bilka Horsens");
//
//        b.createAccount(dmr, BigDecimal.valueOf(1000));
//        b.createAccount(bilka, BigDecimal.valueOf(100000));
//
//        for (AccountInfo acc : b.bank.getAccounts()) {
//            System.out.print(acc.getUser().getFirstName() + " ");
//            System.out.println(b.bank.getAccount(acc.getAccountId()).getBalance());
//        }
//
//        b.makeTransaction(dmr, bilka, BigDecimal.valueOf(500), "pizza");
//
//        for (AccountInfo acc : b.bank.getAccounts()) {
//            System.out.print(acc.getUser().getFirstName() + " ");
//            System.out.println(b.bank.getAccount(acc.getAccountId()).getBalance());
//        }
//
//        b.makeTransaction("1111111111", "12", BigDecimal.valueOf(500), "pizza");
//
//        for (AccountInfo acc : b.bank.getAccounts()) {
//            System.out.print(acc.getUser().getFirstName() + " ");
//            System.out.println(b.bank.getAccount(acc.getAccountId()).getBalance());
//        }
//    }

}

