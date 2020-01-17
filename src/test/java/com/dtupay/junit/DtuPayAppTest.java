package com.dtupay.junit;

import com.dtupay.app.*;
import com.dtupay.bank.BankAdapter;
import com.dtupay.bank.IBankAdapter;
import com.dtupay.bank.exceptions.BankAdapterException;
import com.dtupay.database.*;
import com.dtupay.database.exceptions.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class DtuPayAppTest {
    IBankAdapter bank;
    ICustomerAdapter customerAdapter;
    IMerchantAdapter merchantAdapter;
    ITokenAdapter tokenAdapter;
    IDtuPayApp dtupay;
    ITokenManagement tokenManager;
    Merchant merchant;
    Customer customer;
    Token token;
    String description;

    @Before
    public void Setup() throws MerchantDoesNotExist, BankAdapterException {
        bank = new BankAdapter();
        customerAdapter = new CustomerAdapter();
        merchantAdapter = new MerchantAdapter();
        tokenAdapter = new TokenAdapter();
        tokenManager = new TokenManagement();
        dtupay = new DtuPayApp(bank, customerAdapter, merchantAdapter, tokenAdapter);

        merchant = merchantAdapter.createMerchant("1234536", "Casper1");
        customer = customerAdapter.createCustomer("1003245", "Casper2");
        token = new Token(1, tokenManager.GetToken(), customer.getId());
        tokenAdapter.createToken(token);
        description = "A proper meal";
    }

    @After
    public void cleanUp() throws BankAdapterException {
        bank.deleteAllCreatedAccounts();
    }

    @Test
    public void checkTokenValidityOfUnusedToken() throws CustomerHasNoUnusedToken, FakeToken, TokenAlreadyUsed {
        Token token = tokenAdapter.getUnusedTokenByCustomerId(1);
        Assert.assertTrue(dtupay.checkTokenValidity(token));
    }

    @Test(expected = TokenAlreadyUsed.class)
    public void checkTokenValidityOfUsedToken() throws CustomerHasNoUnusedToken, FakeToken, TokenAlreadyUsed {
        Token token = tokenAdapter.getUnusedTokenByCustomerId(1);
        token.setUsed(true);
        dtupay.checkTokenValidity(token);
    }

    @Test(expected = FakeToken.class)
    public void checkTokenValidityOfTokenThatDoesNotExistInTokenDatabase() throws FakeToken, TokenAlreadyUsed {
        Token token = new Token(2, tokenManager.GetToken(), 1);
        dtupay.checkTokenValidity(token);
    }

    @Test(expected = FakeToken.class)
    public void checkTokenValidityOfTokenFromAnotherCustomer() throws CustomerHasNoUnusedToken, FakeToken, TokenAlreadyUsed {
        Token token1 = tokenAdapter.getUnusedTokenByCustomerId(1);
        Token token2 = new Token(3, token1.getUuid(), 2);
        dtupay.checkTokenValidity(token2);
    }

    @Test
    public void transferMoneyFromExistingCustomerToExistingMerchant() throws BankAdapterException {
        BigDecimal amount = new BigDecimal(200.0);

        bank.createAccount(merchant.getName(), merchant.getCvr(), new BigDecimal(200.0));
        bank.createAccount(customer.getName(), customer.getCpr(), new BigDecimal(200.0));
        dtupay.transferMoney(merchant.getCvr(), token, amount, description);

        BigDecimal balance = bank.getBalanceByCPR(merchant.getCvr());
        Assert.assertEquals(new BigDecimal(400.0), balance);
    }

    @Test(expected = BankAdapterException.class)
    public void transferMoneyFromExistingCustomerToMerchantThatDoesNotExist() throws BankAdapterException {
        BigDecimal amount = new BigDecimal(200.0);

        bank.createAccount(customer.getName(), customer.getCpr(), new BigDecimal(200.0));
        dtupay.transferMoney("1", token, amount, description);
    }

    @Test(expected = BankAdapterException.class)
    public void transferMoneyByNegativeAmountFromCustomerToMerchant() throws BankAdapterException {
        BigDecimal amount = new BigDecimal(-200.0);

        bank.createAccount(merchant.getName(), merchant.getCvr(), new BigDecimal(200.0));
        bank.createAccount(customer.getName(), customer.getCpr(), new BigDecimal(200.0));
        dtupay.transferMoney(merchant.getCvr(), token, amount, description);
    }

    @Test(expected = BankAdapterException.class)
    public void transferMoneyFromCustomerWithNegativeBalanceToMerchant() throws BankAdapterException {
        BigDecimal amount = new BigDecimal(200.0);

        bank.createAccount(merchant.getName(), merchant.getCvr(), new BigDecimal(200.0));
        bank.createAccount(customer.getName(), customer.getCpr(), new BigDecimal(-200.0));
        dtupay.transferMoney(merchant.getCvr(), token, amount, description);
    }

}
