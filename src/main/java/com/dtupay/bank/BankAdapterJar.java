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

    @Override
    public void createAccount(Customer customer, int initialBalance) {
        User u = new User();
        u.setFirstName(customer.getName());
        u.setCprNumber(customer.getId());

        Account a = new Account();
        a.setUser(u);
        a.setBalance(BigDecimal.valueOf(initialBalance));

        repo.createAccount(a);
    }

    @Override
    public void createAccount(Merchant merchant, int initialBalance) {
        User u = new User();
        u.setFirstName(merchant.getName());

        Account a = new Account();
        a.setUser(u);
        a.setId(String.valueOf(merchant.getId()));
        a.setBalance(BigDecimal.valueOf(initialBalance));

        repo.createAccount(a);
    }

    @Override
    public void makeTransaction(Customer from, Merchant to, int amount, String comment) throws BankServiceException {
        String customerBankId = repo.readAccountByCpr(from.getId()).getId();
        String merchantBankId = repo.readAccount(String.valueOf(to.getId())).getId();
        bank.transferMoneyFromTo(customerBankId, merchantBankId, BigDecimal.valueOf(amount), comment);
    }

//    public static void main(String[] args) throws Exception {
//        BankAdapterJar b = new BankAdapterJar();
//
//        Customer dmr = new Customer("dmitry", "1111111111");
//        Merchant bilka = new Merchant("12", "Bilka Horsens");
//
//        b.createAccount(dmr, 1000);
//        b.createAccount(bilka, 100000);
//
//        for (AccountInfo acc : b.bank.getAccounts()) {
//            System.out.print(acc.getUser().getFirstName() + " ");
//            System.out.println(b.bank.getAccount(acc.getAccountId()).getBalance());
//        }
//
//        b.makeTransaction(dmr, bilka, 500, "pizza");
//
//        for (AccountInfo acc : b.bank.getAccounts()) {
//            System.out.print(acc.getUser().getFirstName() + " ");
//            System.out.println(b.bank.getAccount(acc.getAccountId()).getBalance());
//        }
//    }

}

