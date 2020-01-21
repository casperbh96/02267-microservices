package com.dtupay.junit;

import com.dtupay.DTUPayApp;
import com.dtupay.IDTUPayApp;
import com.dtupay.adapters.customer.CustomerAdapter;
import com.dtupay.adapters.customer.ICustomerAdapter;
import com.dtupay.adapters.customer.model.Customer;
import com.dtupay.adapters.merchant.IMerchantAdapter;
import com.dtupay.adapters.merchant.MerchantAdapter;
import com.dtupay.adapters.merchant.model.Merchant;
import com.dtupay.adapters.token.ITokenAdapter;
import com.dtupay.adapters.token.TokenAdapter;
import com.dtupay.adapters.token.exceptions.TokenException;
import com.dtupay.adapters.token.model.Token;
import com.dtupay.adapters.bank.BankAdapter;
import com.dtupay.adapters.bank.IBankAdapter;
import com.dtupay.adapters.bank.exceptions.BankAdapterException;
import com.dtupay.adapters.transaction.ITransactionAdapter;
import com.dtupay.adapters.transaction.TransactionAdapter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


/**
 * tests for dtuPayApp
 */
public class DtuPayAppTest {
    IBankAdapter bankAdapter;
    ICustomerAdapter customerAdapter;
    IMerchantAdapter merchantAdapter;
    ITokenAdapter tokenAdapter;
    ITransactionAdapter transactionAdapter;

    IDTUPayApp dtupay;

    /**
     * setting up dtupay app
     */
    @Before
    public void Setup() {
        bankAdapter = Mockito.mock(BankAdapter.class);
        customerAdapter = Mockito.mock(CustomerAdapter.class);
        merchantAdapter = Mockito.mock(MerchantAdapter.class);
        tokenAdapter = Mockito.mock(TokenAdapter.class);
        transactionAdapter = Mockito.mock(TransactionAdapter.class);

        dtupay = new DTUPayApp(bankAdapter, customerAdapter, merchantAdapter, tokenAdapter, transactionAdapter);
    }

    /**
     * correct method calling by create costumer
     * @throws Throwable
     */
    @Test
    public void createCustomer_Should_CallTheCorrectMethod() throws Throwable {
        String cpr = "123456";
        String name = "Adotp Dirut";
        dtupay.createCustomer(cpr, name);
        Mockito.verify(customerAdapter).createCustomer(cpr, name);
    }

    /**
     * correct method calling by update costumer
     * @throws Throwable
     */
    @Test
    public void updateCustomer_Should_CallTheCorrectMethod() throws Throwable {
        int id = 123;
        String cpr = "123456";
        String name = "Adotp Dirut";
        dtupay.updateCustomer(id, cpr, name);
        Mockito.verify(customerAdapter).updateCustomer(id, cpr, name);
    }

    /**
     * checks if function is generating exception when customer is not allowed to requests for more token even though requesting more.
     * @throws Throwable
     */
    @Test(expected = TokenException.class)
    public void getMoreTokens_Should_ThrowAnException_When_TokensAreNotAllowed() throws Throwable {
        int id = 123;
        int numOfTokens = 3;

        Customer customerMock = Mockito.mock(Customer.class);

        Token tokenMock1 = Mockito.mock(Token.class);
        Mockito.when(tokenMock1.getUsed()).thenReturn(false);

        Token tokenMock2 = Mockito.mock(Token.class);
        Mockito.when(tokenMock2.getUsed()).thenReturn(true);

        Token tokenMock3 = Mockito.mock(Token.class);
        Mockito.when(tokenMock3.getUsed()).thenReturn(false);

        Token tokenMock4 = Mockito.mock(Token.class);
        Mockito.when(tokenMock4.getUsed()).thenReturn(true);

        Token[] t = {tokenMock1, tokenMock2, tokenMock3, tokenMock4};
        List<Token> tokenMocks = Arrays.asList(t);

        Mockito.when(customerAdapter.getCustomerById(id)).thenReturn(customerMock);
        Mockito.when(customerMock.getTokens()).thenReturn(tokenMocks);

        dtupay.getMoreTokens(id, numOfTokens);
    }

    /**
     * checks for customer requesting tokens when is allowed to requests for more token.
     * @throws Throwable
     */
    @Test
    public void getMoreTokens_Should_AskForMoreTokens_When_TokensAreAllowed() throws Throwable {
        int id = 123;
        int numOfTokens = 3;

        Customer customerMock = Mockito.mock(Customer.class);

        Token tokenMock1 = Mockito.mock(Token.class);
        Mockito.when(tokenMock1.getUsed()).thenReturn(true);

        Token tokenMock2 = Mockito.mock(Token.class);
        Mockito.when(tokenMock2.getUsed()).thenReturn(true);

        Token tokenMock3 = Mockito.mock(Token.class);
        Mockito.when(tokenMock3.getUsed()).thenReturn(false);

        Token tokenMock4 = Mockito.mock(Token.class);
        Mockito.when(tokenMock4.getUsed()).thenReturn(true);

        Token[] t = {tokenMock1, tokenMock2, tokenMock3, tokenMock4};
        List<Token> tokenMocks = Arrays.asList(t);

        Mockito.when(customerAdapter.getCustomerById(id)).thenReturn(customerMock);
        Mockito.when(customerMock.getTokens()).thenReturn(tokenMocks);

        dtupay.getMoreTokens(id, numOfTokens);
        Mockito.verify(tokenAdapter).getNewTokensForCustomer(id, numOfTokens);
    }

    /**
     * checks if generateMonthlyCustomerReport is calling the right method
     * @throws Throwable
     */
    @Test
    public void generateMonthlyCustomerReport_Should_CallTheCorrectMethod() throws Throwable {
        int id = 123;
        int month = 4;
        int year = 1970;
        dtupay.generateMonthlyCustomerReport(id, month, year);
        Mockito.verify(transactionAdapter).getMonthlyCustomerReport(id, month, year);
    }

    /**
     * checks if create merchant is calling correct method
     * @throws Throwable
     */
    @Test
    public void createMerchant_Should_CallTheCorrectMethod() throws Throwable {
        String cvr = "123456";
        String name = "Adotp Dirut";
        dtupay.createMerchant(cvr, name);
        Mockito.verify(merchantAdapter).createMerchant(cvr, name);
    }

    /**
     * checks if update merchant is calling correct method
     * @throws Throwable
     */
    @Test
    public void updateMerchant_Should_CallTheCorrectMethod() throws Throwable {
        int id = 123;
        String cvr = "123456";
        String name = "Adotp Dirut";
        dtupay.updateMerchant(id, cvr, name);
        Mockito.verify(merchantAdapter).updateMerchant(id, cvr, name);
    }

    /**
     * checks if generateMonthlyMerchantReport is calling the right method
     * @throws Throwable
     */
    @Test
    public void generateMonthlyMerchantReport_Should_CallTheCorrectMethod() throws Throwable {
        int id = 123;
        int month = 4;
        int year = 1970;
        dtupay.generateMonthlyMerchantReport(id, month, year);
        Mockito.verify(transactionAdapter).getMonthlyMerchantReport(id, month, year);
    }

    /**
     * checks if tranfer of money is not done when token is invalid
     * @throws Throwable
     */
    @Test(expected = TokenException.class)
    public void transferMoney_Should_ThrowAnException_When_TokenIsInvalid() throws Throwable {
        int tokenId = 5;
        Token tokenMock = Mockito.mock(Token.class);
        Mockito.when(tokenMock.getId()).thenReturn(tokenId);

        Mockito.when(tokenAdapter.isTokenValid(tokenId)).thenReturn(false);
        dtupay.transferMoney(0, tokenMock, BigDecimal.valueOf(0), "");
    }

    /**
     * checks if there is no update of transaction when it fails
     * @throws Throwable
     */
    @Test(expected = BankAdapterException.class)
    public void transferMoney_Should_NotSaveTheTransAction_When_TransactionFails() throws Throwable {
        int tokenId = 5;
        int customerId = 123;
        String customerCpr = "123456";
        int merchantId = 456;
        String merchantCvr = "6789098";
        BigDecimal amount = BigDecimal.valueOf(345);
        String description = "Lorem fungi";

        Token tokenMock = Mockito.mock(Token.class);
        Mockito.when(tokenMock.getId()).thenReturn(tokenId);
        Mockito.when(tokenMock.getCustomerId()).thenReturn(customerId);

        Mockito.when(tokenAdapter.isTokenValid(tokenId)).thenReturn(true);

        Customer customerMock = Mockito.mock(Customer.class);
        Mockito.when(customerAdapter.getCustomerById(customerId)).thenReturn(customerMock);
        Mockito.when(customerMock.getCpr()).thenReturn(customerCpr);

        Merchant merchantMock = Mockito.mock(Merchant.class);
        Mockito.when(merchantAdapter.getMerchantById(merchantId)).thenReturn(merchantMock);
        Mockito.when(merchantAdapter.getMerchantById(merchantId).getCvr()).thenReturn(merchantCvr);

        Mockito.doThrow(new BankAdapterException("")).when(bankAdapter).makeTransaction(customerCpr, merchantCvr, amount, description);

        dtupay.transferMoney(merchantId, tokenMock, amount, description);
        Mockito.verify(tokenAdapter).markTokenAsUsed(tokenId);
        Mockito.verify(bankAdapter).makeTransaction(customerCpr, merchantCvr, amount, description);

        Mockito.verify(transactionAdapter, Mockito.never()).registerTransaction((Timestamp) Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), (BigDecimal) Mockito.any(), Mockito.anyBoolean());
    }

    /**
     * checks if there is update of transaction when it passes
     * @throws Throwable
     */
    @Test
    public void transferMoney_Should_PerformTheTransaction_And_SaveTheTransaction_When_TransactionWorks() throws Throwable {
        int tokenId = 5;
        int customerId = 123;
        String customerCpr = "123456";
        int merchantId = 456;
        String merchantCvr = "6789098";
        BigDecimal amount = BigDecimal.valueOf(345);
        String description = "Lorem fungi";

        Token tokenMock = Mockito.mock(Token.class);
        Mockito.when(tokenMock.getId()).thenReturn(tokenId);
        Mockito.when(tokenMock.getCustomerId()).thenReturn(customerId);

        Mockito.when(tokenAdapter.isTokenValid(tokenId)).thenReturn(true);

        Customer customerMock = Mockito.mock(Customer.class);
        Mockito.when(customerAdapter.getCustomerById(customerId)).thenReturn(customerMock);
        Mockito.when(customerMock.getCpr()).thenReturn(customerCpr);

        Merchant merchantMock = Mockito.mock(Merchant.class);
        Mockito.when(merchantAdapter.getMerchantById(merchantId)).thenReturn(merchantMock);
        Mockito.when(merchantAdapter.getMerchantById(merchantId).getCvr()).thenReturn(merchantCvr);

        dtupay.transferMoney(merchantId, tokenMock, amount, description);
        Mockito.verify(tokenAdapter).markTokenAsUsed(tokenId);
        Mockito.verify(bankAdapter).makeTransaction(customerCpr, merchantCvr, amount, description);
        Mockito.verify(transactionAdapter).registerTransaction((Timestamp) Mockito.any(), Mockito.eq(customerId), Mockito.eq(merchantId), Mockito.eq(tokenId), Mockito.eq(amount), Mockito.anyBoolean());
    }

}
