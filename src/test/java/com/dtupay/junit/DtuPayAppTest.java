package com.dtupay.junit;

import com.dtupay.app.*;
import com.dtupay.bank.BankAdapter;
import com.dtupay.bank.IBankAdapter;
import com.dtupay.bank.exceptions.BankAdapterException;
import com.dtupay.database.*;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;
import com.dtupay.database.exceptions.MerchantDoesNotExist;
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
        bank.deleteAllAccounts();
        customerAdapter = new CustomerAdapter();
        merchantAdapter = new MerchantAdapter();
        tokenAdapter = new TokenAdapter();
        tokenManager = new TokenManagement();
        dtupay = new DtuPayApp(bank, customerAdapter, merchantAdapter, tokenAdapter);

        merchant = merchantAdapter.getMerchantByMerchantId("1");
        customer = customerAdapter.createCustomer(new Customer("99", "Casper2"));
        token = new Token(tokenManager.GetToken(), customer.getId());
        tokenAdapter.createToken(token);
        description = "A proper meal";
    }

    @Test
    public void checkTokenValidityOfUnusedToken() throws CustomerHasNoUnusedToken, CustomerDoesNotExist {
        Token token = tokenAdapter.getUnusedTokenByCustomerId("1");
        Assert.assertTrue(dtupay.checkTokenValidity(token));
    }

    @Test
    public void checkTokenValidityOfUsedToken() throws CustomerHasNoUnusedToken, CustomerDoesNotExist {
        Token token = tokenAdapter.getUnusedTokenByCustomerId("1");
        token.setUsed(true);
        Assert.assertFalse(dtupay.checkTokenValidity(token));
    }

    @Test
    public void checkTokenValidityOfTokenThatDoesNotExistInTokenDatabase() {
        Token token = new Token(tokenManager.GetToken(), "1");
        Assert.assertFalse(dtupay.checkTokenValidity(token));
    }

    @Test
    public void checkTokenValidityOfTokenFromAnotherCustomer() throws CustomerHasNoUnusedToken, CustomerDoesNotExist {
        Token token1 = tokenAdapter.getUnusedTokenByCustomerId("1");
        Token token2 = new Token(token1.getId(), "2");
        Assert.assertFalse(dtupay.checkTokenValidity(token2));
    }

    @Test
    public void transferMoneyFromExistingCustomerToExistingMerchant() throws MerchantDoesNotExist, CustomerHasNoUnusedToken, CustomerDoesNotExist, BankAdapterException {
        BigDecimal amount = new BigDecimal(200.0);

        bank.createAccount(merchant.getName(), merchant.getId(), new BigDecimal(200.0));
        bank.createAccount(customer.getName(), customer.getId(), new BigDecimal(200.0));
        dtupay.transferMoney(merchant.getId(), token, amount, description);

        BigDecimal balance = bank.getBalanceByCPR("1");
        Assert.assertEquals(new BigDecimal(400.0), balance);
    }

    @Test(expected = BankAdapterException.class)
    public void transferMoneyFromExistingCustomerToMerchantThatDoesNotExist() throws MerchantDoesNotExist, CustomerHasNoUnusedToken, CustomerDoesNotExist, BankAdapterException {
        BigDecimal amount = new BigDecimal(200.0);

        bank.createAccount(customer.getName(), customer.getId(), new BigDecimal(200.0));
        dtupay.transferMoney("1", token, amount, description);
    }

    @Test(expected = BankAdapterException.class)
    public void transferMoneyByNegativeAmountFromCustomerToMerchant() throws BankAdapterException, MerchantDoesNotExist {
        BigDecimal amount = new BigDecimal(-200.0);

        bank.createAccount(merchant.getName(), merchant.getId(), new BigDecimal(200.0));
        bank.createAccount(customer.getName(), customer.getId(), new BigDecimal(200.0));
        dtupay.transferMoney(merchant.getId(), token, amount, description);
    }

    @Test(expected = BankAdapterException.class)
    public void transferMoneyFromCustomerWithNegativeBalanceToMerchant() throws BankAdapterException, MerchantDoesNotExist {
        BigDecimal amount = new BigDecimal(200.0);

        bank.createAccount(merchant.getName(), merchant.getId(), new BigDecimal(200.0));
        bank.createAccount(customer.getName(), customer.getId(), new BigDecimal(-200.0));
        dtupay.transferMoney(merchant.getId(), token, amount, description);
    }

}
