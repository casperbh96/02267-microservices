package com.dtupay;

import com.dtupay.app.*;
import com.dtupay.bank.BankAdapterJar;
import com.dtupay.bank.IBankAdapter;
import com.dtupay.database.*;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.CustomerHasNoUnusedToken;
import com.dtupay.database.exceptions.MerchantDoesNotExist;
import dtu.ws.fastmoney.Bank;
import dtu.ws.fastmoney.BankServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DtuPayAppTest {
    IBankAdapter bank;
    ICustomerAdapter customers;
    IMerchantAdapter merchants;
    ITokenAdapter tokens;
    IDtuPayApp dtupay;
    ITokenManagement tokenManager;

    @Before
    public void Setup(){
        bank = new BankAdapterJar();
        customers = new CustomerAdapter();
        merchants = new MerchantAdapter();
        tokens = new TokenAdapter();
        tokenManager = new TokenManagement();
        dtupay = new DtuPayApp(bank, customers, merchants, tokens);
    }

    @Test
    public void checkTokenValidityOfUnusedToken() throws CustomerHasNoUnusedToken, CustomerDoesNotExist {
        Token token = tokens.getUnusedTokenByCustomerId("1");
        Assert.assertTrue(dtupay.checkTokenValidity(token));
    }

    @Test
    public void checkTokenValidityOfUsedToken() throws CustomerHasNoUnusedToken, CustomerDoesNotExist {
        Token token = tokens.getUnusedTokenByCustomerId("1");
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
        Token token1 = tokens.getUnusedTokenByCustomerId("1");
        Token token2 = new Token(token1.getId(), "2");
        Assert.assertFalse(dtupay.checkTokenValidity(token2));
    }

    @Test
    public void transferMoneyFromExistingCustomerToExistingMerchant() throws MerchantDoesNotExist, CustomerHasNoUnusedToken, CustomerDoesNotExist, BankServiceException {
        Merchant merchant = merchants.getMerchantByMerchantId("1");
        Customer customer = customers.createCustomer(new Customer("99", "Casper2"));

        Token token = new Token(tokenManager.GetToken(), customer.getId());
        tokens.createToken(token);
        BigDecimal amount = new BigDecimal(200.0);
        String description = "A proper meal";

        bank.createAccount(merchant.getName(), merchant.getId(), new BigDecimal(200.0));
        bank.createAccount(customer.getName(), customer.getId(), new BigDecimal(200.0));
        dtupay.transferMoney(merchant.getId(), token, amount, description);

        BigDecimal balance = bank.getBalanceByCPR("1");
        Assert.assertEquals(new BigDecimal(400.0), balance);
    }

    @Test(expected=NullPointerException.class)
    public void transferMoneyFromExistingCustomerToMerchantThatDoesNotExist() throws MerchantDoesNotExist, CustomerHasNoUnusedToken, CustomerDoesNotExist, BankServiceException {
        Customer customer = customers.createCustomer(new Customer("99", "Casper2"));

        Token token = new Token(tokenManager.GetToken(), customer.getId());
        tokens.createToken(token);
        BigDecimal amount = new BigDecimal(200.0);
        String description = "A proper meal";

        bank.createAccount(customer.getName(), customer.getId(), new BigDecimal(200.0));
        dtupay.transferMoney("1", token, amount, description);
    }

    @Test(expected=BankServiceException.class)
    public void transferMoneyByNegativeAmountFromCustomerToMerchant() throws BankServiceException, MerchantDoesNotExist {
        Merchant merchant = merchants.getMerchantByMerchantId("1");
        Customer customer = customers.createCustomer(new Customer("99", "Casper2"));

        Token token = new Token(tokenManager.GetToken(), customer.getId());
        tokens.createToken(token);
        BigDecimal amount = new BigDecimal(-200.0);
        String description = "A proper meal";

        bank.createAccount(merchant.getName(), merchant.getId(), new BigDecimal(200.0));
        bank.createAccount(customer.getName(), customer.getId(), new BigDecimal(200.0));
        dtupay.transferMoney(merchant.getId(), token, amount, description);
    }

    @Test(expected=BankServiceException.class)
    public void transferMoneyFromCustomerWithNegativeBalanceToMerchant() throws BankServiceException, MerchantDoesNotExist {
        Merchant merchant = merchants.getMerchantByMerchantId("1");
        Customer customer = customers.createCustomer(new Customer("99", "Casper2"));

        Token token = new Token(tokenManager.GetToken(), customer.getId());
        tokens.createToken(token);
        BigDecimal amount = new BigDecimal(200.0);
        String description = "A proper meal";

        bank.createAccount(merchant.getName(), merchant.getId(), new BigDecimal(200.0));
        bank.createAccount(customer.getName(), customer.getId(), new BigDecimal(-200.0));
        dtupay.transferMoney(merchant.getId(), token, amount, description);
    }

}
